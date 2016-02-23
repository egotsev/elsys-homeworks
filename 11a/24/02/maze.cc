#ifndef MAZE_H
#define MAZE_H

#include "maze_path.cc"
#include <iostream>
#include <vector>
#include <cstdlib>


using namespace std;

enum Direction {
    UP = 1,
    RIGHT = 1 << 1,
    DOWN = 1 << 2,
    LEFT = 1 << 3
};

class MazeError {};

class Cell : public Shape{
    friend class Board;

    int row_, col_;
    int length_;
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
    
    Cell(int row, int col, int size) : visited_(false), row_(row), col_(col), length_(size),
    walls_(UP | RIGHT | DOWN | LEFT),
    up_(0), right_(0), down_(0), left_(0){}
    
    void drill(Direction dir) {
        //dir = 1000
        //~dir = 0111
        //walls_ = 1111
        //wall_ & ~dir = 0111
        walls_ &= ~dir;
    }
    
    void drill_to(Cell* next) {
        if (next == up()) {
            drill(UP);
            next->drill(DOWN);
        } else if (next == right()) {
            drill(RIGHT);
            next->drill(LEFT);
        } else if (next == down()) {
            drill(DOWN);
            next->drill(UP);
        } else if (next = left()) {
            drill(LEFT);
            next->drill(RIGHT);
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
    
    void draw() const {
    		Path p;
    		p.add_option("M",new Point(col_*length_,row_*length_));
    		p.add_option((has_wall(DOWN) ? "L" : "M"),new Point(length_,0));
    		p.add_option((has_wall(RIGHT) ? "L" : "M"),new Point(0,length_));
    		p.add_option((has_wall(UP) ? "L" : "M"),new Point(-length_,0));
    		p.add_option((has_wall(LEFT) ? "L" : "M"),new Point(0,-length_));
    		
    		p.draw();
//        cout << length << " " << 0 << (has_wall(DOWN) ? " rlineto" : " rmoveto") << endl;
//        cout << 0 << " " << length << (has_wall(RIGHT) ? " rlineto" : " rmoveto")  << endl;
//        cout << -length << " " << 0 << (has_wall(UP) ? " rlineto" : " rmoveto") << endl;
//        cout << 0 << " " << -length << (has_wall(LEFT) ? " rlineto" : " rmoveto") << endl;
    }
    
    Cell* up() {
        return up_;
    }
    
    Cell* right() {
        return right_;
    }
    
    Cell* down() {
        return down_;
    }
    
    Cell* left() {
        return left_;
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
        vector<Cell*> neighbours;
        if (up() != 0 && !up()->is_visited()) {
            neighbours.push_back(up());
        }
        if (right() != 0 && !right()->is_visited()) {
            neighbours.push_back(right());
        }
        if (down() != 0 && !down()->is_visited()) {
            neighbours.push_back(down());
        }
        if (left() != 0 && !left()->is_visited()) {
            neighbours.push_back(left());
        }
        return neighbours;
    }
    
    Cell* get_random_unvisited_neighbour() {
        if (!has_unvisited_neighbours()) {
            throw MazeError();
        }
        vector<Cell*> neighbours = get_unvisited_neighbours();
        int index = rand() % neighbours.size();
        return neighbours[index];
    }
};

class Board {
    vector<Cell> cells_;
    int rows_, cols_;
    int cell_size_;
    
    public:
    
    Board(int rows, int cols, int cell_size) : rows_(rows), cols_(cols), cell_size_(cell_size) {
        for (int i = 0; i < rows_; i++) {
            for(int j = 0; j < cols_; j++) {
                cells_.push_back(Cell(i, j, cell_size_));
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
		
		Canvas c(rows_*cell_size_,cols_*cell_size_);

		for(vector<Cell>::iterator it = cells_.begin();
			it!=cells_.end(); ++it) {
		
			c.add(&(*it));	
		}
		
		c.draw();
		
		//cout << "stroke" << endl;
		//cout << "showpage" << endl;
		
	}
};

void generate_maze(Board& board, Cell& start) {
    start.visit();
    while (start.has_unvisited_neighbours()) {
        Cell* next = start.get_random_unvisited_neighbour();
        start.drill_to(next);
        generate_maze(board, *next);
    }
}


int main() {
		int rows = 30,cols = 30;
		int cell_size = 20;
    Board board(rows, cols, cell_size);
    Cell& start = board.at(0, 0);
    generate_maze(board, start);
		board.draw();
    return 0;
}

#endif
