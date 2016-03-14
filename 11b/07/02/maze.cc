#include <iostream>
#include <vector>
#include <cstdlib>
#include "figure.cc"

using namespace std;

enum Direction {
    UP = 1,
    RIGHT = 1 << 1,
    DOWN = 1 << 2,
    LEFT = 1 << 3
};

class BoardError {};

class Cell : public Shape{
    friend class Board;
    int row_, col_, step_;
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
	Cell(int row, int col, int step=10) 
    : visited_(false), row_(row), col_(col), step_(step),
    walls_(UP | RIGHT | DOWN | LEFT),
    up_(0), right_(0), down_(0), left_(0)
    {}
    
    ~Cell() {
    	delete up_;
    	delete down_;
    	delete right_;
    	delete left_;
    }

    void drill(Direction dir) {
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
            throw BoardError();
        }
    }

    bool has_wall(Direction dir) const {
        return walls_ & dir;
    }

    void draw() const{
    	Path path_;
		path_.add(Path::Option("M", col_*step_));
		path_.add(Path::Option("", row_*step_));
		path_.add(Path::Option((has_wall(DOWN)?"l":"m"), step_));
		path_.add(Path::Option("", 0));
		path_.add(Path::Option((has_wall(RIGHT)?"l":"m"), 0));
		path_.add(Path::Option("", step_));
		path_.add(Path::Option((has_wall(UP)?"l":"m"), -step_));
		path_.add(Path::Option("", 0));
		path_.add(Path::Option((has_wall(LEFT)?"l":"m"), 0));
		path_.add(Path::Option("", -step_));
		path_.set_property("stroke", "blue");
		path_.set_property("fill", "none");
		path_.draw();
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

    void set_visited() {
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
            throw BoardError();
        }
        vector<Cell*> neighbours = get_unvisited_neighbours();
        int index = rand() % neighbours.size();
        return neighbours[index];
    }
};

class Board {
    vector<Cell> cells_;
    int rows_, cols_, size_;
public:
	Board(int rows, int cols, int size) 
    : rows_(rows), cols_(cols), size_(size)
    {
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

	~Board() {
		for (vector<Cell>::iterator it = cells_.begin(); it != cells_.end(); it++) {
            delete &*it;
        }
	}
	
	
    Cell& at(int i, int j) {
    	int index = i*cols_ + j;
		return cells_[index];
    }
    
    const Cell& at(int i, int j) const {
		int index = i*cols_ + j;
		return cells_[index];		
	}

    void draw(){
      Canvas canvas(rows_*size_, cols_*size_);
  		for(vector<Cell>::iterator it = cells_.begin();it!=cells_.end(); ++it) {
        	canvas.add(&*it);
  		}
      canvas.draw();
	}
};

class Maze {
	Board b_;
	
	void generate(Cell& c) {
		c.set_visited();
		while(c.has_unvisited_neighbours()) {
			Cell* next=c.get_random_unvisited_neighbour();
			next->set_visited();
			c.drill_to(next);
			generate(*next);
		}
	}
public:
	Maze(int r, int c, int step)
	: b_(Board(r, c, step))
	{}
	
	void create(int i=0, int j=0) {
		Cell& cell = b_.at(i, j);
		generate(cell);
		b_.draw();
	}	
};

int main() {
    Maze m(30, 30, 20);
    m.create(2,2);
    return 0;
}
