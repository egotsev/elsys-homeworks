#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>

#include "figures.cc"

using namespace std;

enum Dir {
	LEFT = 1,
	RIGHT = 1 << 1,
	UP = 1 << 2,
	DOWN = 1 << 3
};

class BoardError {};

class Cell : public Shape {
	friend class Board;

	int row_;
	int col_;
	bool visited_;
	unsigned int walls_;

	Cell* up_;
	Cell* down_;
	Cell* left_;
	Cell* right_;

	Cell& set_up(Cell* up) {
		up_ = up;

		return *this;
	}
	Cell& set_down(Cell* down) {
		down_ = down;

		return *this;
	}
	Cell& set_left(Cell* left) {
		left_ = left;

		return *this;
	}
	Cell& set_right(Cell* right) {
		right_ = right;

		return *this;
	}
public:
	Cell(int row = -1, int col = -1) : row_(row), col_(col),  visited_(false), walls_(LEFT | RIGHT | UP | DOWN), up_(0), down_(0), left_(0), right_(0) {}

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

	void drill_to(Cell* neighbour) {
		if(neighbour == up()) {
			drill(UP);
			neighbour->drill(DOWN);
		} else if(neighbour == down()) {
			drill(DOWN);
			neighbour->drill(UP);
		} else if(neighbour == left()) {
			drill(LEFT);
			neighbour->drill(RIGHT);
		} else if(neighbour == right()) {
			drill(RIGHT);
			neighbour->drill(LEFT);
		} else {
			throw BoardError();
		}
	}

	void draw() const {
		int step = 10;

		list<pair<char, Point> > listt;

		listt.push_back(make_pair('M', Point(col_ * step, row_ * step)));

		listt.push_back(make_pair(has_wall(DOWN)? 'l' : 'm', Point(step, 0)));

		listt.push_back(make_pair(has_wall(RIGHT)? 'l' : 'm', Point(0, step)));

		listt.push_back(make_pair(has_wall(UP)? 'l' : 'm', Point(-step, 0)));

		listt.push_back(make_pair(has_wall(LEFT)? 'l' : 'm', Point(0, -step)));

		Path* path = new Path(listt);
		path -> draw();

		/*
		cout << col_ * step << " " << row_ * step << " " << " moveto" << endl;

		cout << step << " " << 0 << " " << (has_wall(DOWN)? "rlineto" : "rmoveto") << endl;

		cout << 0 << " " << step << " " << (has_wall(RIGHT)? "rlineto" : "rmoveto") << endl;

		cout << -step << " " << 0 << " " << (has_wall(UP)? "rlineto" : "rmoveto") << endl;

		cout << 0 << " " << -step << " " << (has_wall(LEFT)? "rlineto" : "rmoveto") << endl;
		*/
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

		if(up() != 0 && !(up()->visited())) {
			result.push_back(up());
		}
		if(down() != 0 && !(down()->visited())) {
			result.push_back(down());
		}
		if(left() != 0 && !(left()->visited())) {
			result.push_back(left());
		}
		if(right() != 0 && !(right()->visited())) {
			result.push_back(right());
		}

		return result;
	}

	bool has_unvisited_neighbours() {
		bool does_it = !(get_unvisited_neighbours().empty());

		return does_it;
	}

	Cell* get_random_unvisited_neighbour() {
		if(!has_unvisited_neighbours()) {
			throw BoardError();
		}

		vector<Cell*> neighbours = get_unvisited_neighbours();
		int random = rand() % neighbours.size();

		return neighbours[random];
	}

};

class Board {
	friend class Cell;
	int rows_;
	int cols_;
	vector<Cell> cells_;
public:
	Board(int rows, int cols) : rows_(rows), cols_(cols) {
		for(int i = 0; i < rows_; i++) {
			for(int j = 0; j < cols_; j++) {
				cells_.push_back(Cell(i, j));
			}
		}

		for(int i = 0; i < rows_ ; i++) {
			for(int j = 0; j < cols_ ; j++) {
				Cell& c = at(i, j);

				if(i < rows_ - 1) {
					c.set_up(&at(i + 1, j));
				}

				if(i > 0) {
					c.set_down(&at(i - 1, j));
				}
				
				if(j > 0) {
					c.set_left(&at(i, j - 1));
				}

				if(j < cols_ - 1) {
					c.set_right(&at(i, j + 1));
				}
			}
		}
	}

	Cell& at(int i, int j) {
		int index = (i * cols_) + j;

		return cells_[index];
	}

	const Cell& at(int i, int j) const {
		int index = (i * cols_) + j;

		return cells_[index];	
	}

	void draw() {
		Canvas obj(20 * rows_, 20 * cols_);

		for(vector<Cell>::iterator it = cells_.begin(); it != cells_.end(); it++) {
			obj.add(&(*it));
		}

		obj.draw();

		/*
		cout << "newpath" << endl;
		for (vector<Cell>::const_iterator it = cells_.begin(); it != cells_.end(); it++) {
			(*it).Cell::draw();
		}

		cout << "stroke" << endl;
		cout << "showpage" << endl;
		*/
	}
};

void generate(Board& board, Cell& cell) {
	cell.set_visited();

	while(cell.has_unvisited_neighbours()) {
		Cell* next = cell.get_random_unvisited_neighbour();
		next->set_visited();
		cell.drill_to(next);

		generate(board, *next);
	}
}

int main() {
	Board board(10, 10);
	Cell& start = board.at(0, 0);
	generate(board, start);
	board.draw();

	/*
	Cell c1(1,1);
	cout << c1.visited() << endl;
	cout << c1.has_wall(UP) << endl;
	cout << c1.has_wall(LEFT) << endl;

	c1.drill(LEFT);

	cout << c1.has_wall(UP) << endl;
	cout << c1.has_wall(LEFT) << endl;	
	*/

	/*
	Board b(20, 20);

	Cell& c = b.at(0,0);
	generate(b, c);

	b.draw();
	*/
	return 0;
}