#include <iostream>
#include <vector>
#include <cstdlib>
#include <string>
#include "figures.cc"

using namespace std;

enum Dir {
	LEFT = 1,
  RIGHT = 1<<1,
	UP = 1 << 2,
	DOWN = 1 << 3,
};

class BoardError{};

class Cell {
	friend class Board;
	int row_;
	int col_;
	bool visited_;
	unsigned int walls_;
  Path path_;

	Cell* up_;
	Cell* down_;
	Cell* left_;
	Cell* right_;

	Cell& set_up(Cell* up) {
		up_ = up;
		return *this;
	}

	Cell& set_down(Cell* down) {
		down_=down;
		return *this;
	}

	Cell& set_left(Cell* left) {
		left_=left;
		return *this;
	}

	Cell& set_right(Cell* right) {
		right_ = right;
		return *this;
	}

public:
	Cell(int row=-1, int col=-1)
	: row_(row), col_(col), visited_(false),
	  walls_(LEFT | RIGHT | UP | DOWN),
	  up_(0), down_(0), left_(0), right_(0)
	{}

	bool visited() const {
		return visited_;
	}

	void set_visited() {
		visited_ = true;
	}

	bool has_wall(Dir dir) const {
		return walls_ & dir;
	}

	void drill(Dir dir) {
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

  string int_to_string(int i){
      ostringstream stream;
      stream << i;
      return stream.str();
  }

	void draw(int step=10) {
		path_.add("M"+int_to_string(col_*step)+" "+int_to_string(row_*step));
		path_.add((has_wall(DOWN)?"l":"m")+int_to_string(step)+" "+int_to_string(0));
		path_.add((has_wall(RIGHT)?"l":"m")+int_to_string(0)+" "+int_to_string(step));
		path_.add((has_wall(UP)?"l":"m")+int_to_string(-step)+" "+int_to_string(0));
    path_.add((has_wall(LEFT)?"l":"m")+int_to_string(0)+" "+int_to_string(-step));
	}

  void draw_in_svg() {
    Drawing drawing(800, 800);
    drawing.add(new Path(path_));
    drawing.draw();
  }

	Cell* up() const {
		return up_;
	}
	Cell* down() const {
		return down_;
	}
	Cell* left() const {
		return left_;
	}
	Cell* right() const {
		return right_;
	}

	vector<Cell*> get_unvisited_neighbours() {
		vector<Cell*> result;
		if( up() != 0 && ! up()->visited() )
			result.push_back(up());
		if( down() != 0 && ! down()->visited() )
			result.push_back(down());
		if( left() != 0 && ! left()->visited() )
			result.push_back(left());
		if( right() != 0 && ! right()->visited() )
			result.push_back(right());
		return result;
	}

	bool has_unvisited_neighbours() {
		return ! (get_unvisited_neighbours().empty());
	}

	Cell* get_random_unvisited_neighbour() {
		if(!has_unvisited_neighbours()) {
			throw BoardError();
		}
		vector<Cell*> neighbours = get_unvisited_neighbours();
		int r = rand() % neighbours.size();
		return (neighbours[r]);
	}
};

class Board {
	friend class Cell;
	int rows_;
	int cols_;
	vector<Cell> cells_;

public:
	Board(int rows, int cols)
	: rows_(rows), cols_(cols)
	{
		for(int i=0; i<rows_; ++i) {
			for(int j=0; j<cols_; ++j) {
				cells_.push_back(Cell(i, j));
			}
		}

		for(int i=0; i<rows_; ++i) {
			for(int j=0; j<cols_; ++j) {
				Cell& c=at(i,j);
				if(i < rows_-1)
					c.set_up(&at(i+1,j));
				if(i > 0)
					c.set_down(&at(i-1,j));
				if(j > 0)
					c.set_left(&at(i, j-1));
				if(j < cols_-1)
					c.set_right(&at(i, j+1));
			}
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

	void draw(int size=10){
		cout << "<svg xmlns=\"http://www.w3.org/2000/svg\" fill=\"white\" fill-opacity=\"0\" stroke=\"#000\" stroke-width=\"1.5\" >" << endl; //draw path without fill
		for(vector<Cell>::iterator it = cells_.begin();
			it!=cells_.end(); ++it) {
			(*it).draw(size);
      (*it).draw_in_svg();
		}
		cout << "</svg>" << endl;
	}

};

void generate(Board& b, Cell& c) {
	c.set_visited();
	while(c.has_unvisited_neighbours()) {
		Cell* next=c.get_random_unvisited_neighbour();
		next->set_visited();
		c.drill_to(next);
		generate(b, *next);
	}
}

int main() {
	Board b(40,40);
	Cell& c = b.at(0,0);
	generate(b, c);
	b.draw(10);

	return 0;
}
