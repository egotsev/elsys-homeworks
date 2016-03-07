#include <iostream>
#include <vector>
#include <cstdlib>
#include "figures.cc"

using namespace std;

enum Direction {
    UP = 1,
    RIGHT = 1 << 1,
    DOWN = 1 << 2,
    LEFT = 1 << 3
};

class MazeError{};

class Cell {
    friend class Board;

    int row_, col_;
    bool visited_;
    int walls_;
    Cell* up_;
    Cell* right_;
    Cell* down_;
    Cell* left_;
    
    void set_up(Cell* c) {
        up_ = c;
    }
    
    void set_down(Cell* c) {
        down_ = c;
    }
    
    void set_right(Cell* c) {
        right_ = c;
    }
    
    void set_left(Cell* c) {
        left_ = c;
    }
    
    public:
    
    Cell(int row, int col) : visited_(false), row_(row), col_(col), walls_(UP | RIGHT | DOWN | LEFT),
    up_(0), right_(0), down_(0), left_(0) {}
    
    void drill(Direction dir) {
        //dir = 1000
        //~dir = 0111
        //walls_ = 1111
        //wall_ & ~dir = 0111
        walls_ &= ~dir;
    }
    
    void drill_to(Cell* n) {
        if(n == up()) {
            drill(UP);
            n->drill(DOWN);
        } else if(n==right()) {
            drill(RIGHT);
            n->drill(LEFT);
        } else if(n==down()) {
            drill(DOWN);
            n->drill(UP);
        } else if(n==left()) {
            drill(LEFT);
            n->drill(RIGHT);
        } else {
            throw MazeError();
        }
    }

    bool has_wall(Direction dir) const {
        // walls_ = 1011
        // dir =    1000 => 1000
        // dir =    0010 => 0010
        // dir =    0100 => 0000
        return walls_ & dir;
    }

    /*
    void draw(int length) const {
        cout << row_*length << " " << col_*length << " moveto" << endl;
        cout << length << " " << 0 << (has_wall(DOWN) ? " rlineto" : " rmoveto") << endl;
        cout << 0 << " " << length << (has_wall(RIGHT) ? " rlineto" : " rmoveto")  << endl;
        cout << -length << " " << 0 << (has_wall(UP) ? " rlineto" : " rmoveto") << endl;
        cout << 0 << " " << -length << (has_wall(LEFT) ? " rlineto" : " rmoveto") << endl;
    }
    */

    void draw(Path* path, int len = 10) const {
        path->add(Path::Option("M", col_*len));
        path->add(Path::Option("", row_*len));

        path->add(Path::Option((has_wall(DOWN) ? "l" : "m"), len));
        path->add(Path::Option("", 0));

        path->add(Path::Option((has_wall(RIGHT) ? "l" : "m"), 0));
        path->add(Path::Option("", len));

        path->add(Path::Option((has_wall(UP) ? "l" : "m"), -len));
        path->add(Path::Option("", 0));

        path->add(Path::Option((has_wall(LEFT) ? "l" : "m"), 0));
        path->add(Path::Option("", -len));
    }

    Cell* up() {
        return up_;
    }
    Cell* down() {
        return down_;
    }
    Cell* left() {
        return left_;
    }
    Cell* right() {
        return right_;
    }

    void visit() {
        visited_ = true;
    }
    
    bool is_visited() {
        return visited_; 
    }
    
    bool has_unvisited_neighbours() {
        return !get_unvisited_neighbours().empty();
    }

    vector<Cell*> get_unvisited_neighbours() {
        vector<Cell*> result;
        if( up() != 0 && ! up()->is_visited() ){
            result.push_back(up());
        }
        if( right() != 0 && ! right()->is_visited() ) {
            result.push_back(right());
        }
        if( down() != 0 && ! down()->is_visited() ) { 
            result.push_back(down());
        }
        if( left() != 0 && ! left()->is_visited() ) {
            result.push_back(left());
        }
        return result;
    }
        
    Cell* get_random_unvisited_neighbour() {
        if(!has_unvisited_neighbours()) {
            throw MazeError();
        }
        vector<Cell*> neighbours = get_unvisited_neighbours();
        int r = rand() % neighbours.size();
        return neighbours[r];
    }
};

class Board {
    vector<Cell> cells_;
    int rows_, cols_;
    
    public:
    
    Board(int rows, int cols) : rows_(rows), cols_(cols) {
        for (int i = 0; i < rows_; i++) {
            for(int j = 0; j < cols_; j++) {
                cells_.push_back(Cell(i, j));
            }
        }
        
        for (int i = 0; i < rows_; i++) {
            for(int j = 0; j < cols_; j++) {
                Cell& c = at(i, j);
                if (i < rows_ - 1)
                    c.set_up(&at(i + 1,j));
                if (i > 0)
                    c.set_down(&at(i - 1, j));
                if (j < cols_ - 1)
                    c.set_right(&at(i, j + 1));
                if (j > 0)
                    c.set_left(&at(i, j - 1));
            }
        }
    }
    
    Cell& at(int i, int j) {
        return cells_[i * cols_ + j];
    }
    
    /*
    void draw(int size = 10) const {
		cout << "newpath" << endl;

		for(vector<Cell>::const_iterator it = cells_.begin();
			it!=cells_.end(); ++it) {
		
			(*it).draw(size);	
		}
		
		cout << "stroke" << endl;
		cout << "showpage" << endl;
		
	}
    */

    void draw(int w, int h, Path* path) const {
        for(vector<Cell>::const_iterator it = cells_.begin(); it != cells_.end(); ++it) {
            (*it).draw(path);
        }
    }
};

class Maze {
    int w_, h_;
    Path path_;

    void generate_maze(Board& board, Cell& start) {
        start.visit();
        while (start.has_unvisited_neighbours()) {
            Cell* next = start.get_random_unvisited_neighbour();
            start.drill_to(next);
            generate_maze(board, *next);
        }
    }

public:

    Maze(int w, int h): w_(w), h_(h) {
        path_.add(Path::Option("M", 50));
        path_.add(Path::Option("", 50));
        path_.set_property("stroke", "green").set_property("fill", "none").set_property("stroke-width", "2");
    }

    void draw() {
        Canvas canvas(w_, h_);
        Board board(w_, h_);
        Cell& start = board.at(0, 0);
        generate_maze(board, start);
        board.draw(w_, h_, &path_);
        canvas.add(new Path(path_));
        canvas.draw();
    }
};

int main() {
    Maze maze (300, 300);
    maze.draw();
    return 0;
}