#include <iostream>
#include <vector>
#include <cstdlib>
#include "figures.cc"

using namespace std;

class BoardError{};

enum Direction {
    UP = 1,
    RIGHT = 1 << 1,
    DOWN = 1 << 2,
    LEFT = 1 << 3
};

class Cell : public Shape {
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
    
    Cell(int row, int col, int len) : visited_(false), row_(row), col_(col), walls_(UP | RIGHT | DOWN | LEFT),
    up_(0), right_(0), down_(0), left_(0) {}

    bool visited() const {
        return visited_;
    }

    void set_visited() {
        visited_ = true;
    }
    
    bool has_wall(Direction dir) const {
        return walls_ & dir;
    }

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
        } else if(n==down()) {
            drill(DOWN);
            n->drill(UP);
        } else if(n==left()) {
            drill(LEFT);
            n->drill(RIGHT);
        } else if(n==right()) {
            drill(RIGHT);
            n->drill(LEFT);
        } else {
            throw BoardError();
        }
    }

    void draw(int len = 10) {
        Path path;
        path.add(Path::Option("M", col_*len));
        path.add(Path::Option("", row_*len));
        path.add(Path::Option(has_wall(DOWN) ? "l" : "m", len));
        path.add(Path::Option("", 0));

        path.add(Path::Option(has_wall(RIGHT) ? "l" : "m", 0));
        path.add(Path::Option("", len));

        path.add(Path::Option(has_wall(UP) ? "l" : "m", -len));
        path.add(Path::Option("", 0));

        path.add(Path::Option(has_wall(LEFT) ? "l" : "m", 0));
        path.add(Path::Option("", -len));
        
        path.set_property("stroke", "green");
        path.set_property("fill", "none");
        path.draw();
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

    vector<Cell*> get_unvisited_neighbours() {
        vector<Cell*> result;
        if( up() != 0 && ! up()->visited() )
            result.push_back(up());
        if( right() != 0 && ! right()->visited() )
            result.push_back(right());
        if( down() != 0 && ! down()->visited() ) 
            result.push_back(down());
        if( left() != 0 && ! left()->visited() )
            result.push_back(left());
        return result;
    }
    
    bool has_unvisited_neighbours() {
        return !get_unvisited_neighbours().empty();
    }
    
    Cell* get_random_unvisited_neighbour() {
        if(!has_unvisited_neighbours()) {
            throw BoardError();
        }
        vector<Cell*> neighbours = get_unvisited_neighbours();
        int r = rand() % neighbours.size();
        return neighbours[r];
    }
};

class Board {
    vector<Cell> cells_;
    int rows_, cols_, size_;
    Path path;
    
    public:
    
    Board(int rows, int cols, int size) : rows_(rows), cols_(cols), size_(size) {
        for (int i = 0; i < rows_; i++) {
            for(int j = 0; j < cols_; j++) {
                cells_.push_back(Cell(i, j, size_));
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
    
    void draw() {
		//cout << "newpath" << endl;

        Canvas canvas(size_ * rows_, size_ * cols_);

		for(vector<Cell>::iterator it = cells_.begin(); it!=cells_.end(); ++it) {
		
			//(*it).draw(size);	
		
            canvas.add(&(*it));


        }
		
		//cout << "stroke" << endl;
		//cout << "showpage" << endl;
		
        canvas.draw();

	}
};

void generate(Board& b, Cell& c) {
    c.set_visited();
    while(c.has_unvisited_neighbours()) {
        Cell* next=c.get_random_unvisited_neighbour();
        //next->set_visited();
        c.drill_to(next);
        generate(b, *next);
    }
}


int main() {
    Board b(30, 30, 40);
    Cell& c  = b.at(0, 0);
    generate(b, c);
    b.draw();

    return 0;
}