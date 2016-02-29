#include <vector>
#include <cstdlib>
#include <iostream>
#include <list>
#include <string>
#include <map>

using namespace std;

class Point {
	int x_, y_;
	
	public:
	
	Point(int x=0, int y=0) : x_(x), y_(y) {}
	
	int get_x() const {
		return x_;
	}
	
	int get_y() const {
		return y_;
	}
};
class Drawable {
	public:
	
	virtual void draw() const=0;
};

class Styleable {
	map<string, string> styles_;
	
	public:
	
	Styleable& set_property(string key, string value) {
		styles_.insert(pair<string, string>(key, value));
		return *this;
	}
	
	string get_style() const {
		string result = "";
		for(map<string, string>::const_iterator it = styles_.begin(); it != styles_.end(); it++) {
			result += it->first + "=\"" + it->second + "\" ";
		}
		return result;
	}
};

class Shape : public Drawable, public Styleable {
};

class Path : public Shape {
public:
	class Option {
		string prefix_;
		int number_;

	public:
		Option(string prefix, int number)
		: prefix_(prefix), number_(number)
		{}

		string get_prefix() const{
			return prefix_;
		}

		void set_prefix(string val){
			prefix_ = val;
		}

		int get_number() const{
			return number_;
		}

		void set_number(int val){
			number_ = val;
		}
	};

private:
	list<Option> options_;

public:
	
	Path(list<Option> options)
	: options_(options) {}
	
	Path() {}
	
	void add(Path::Option option) {
		options_.push_back(option);
	}
	
	void draw() const {
		cout << "<path d=\"";

		for(list<Option>::const_iterator it = options_.begin(); it != options_.end(); it++){
			cout << it->get_prefix() << it->get_number() << " ";
		}
		cout << "\" "
			<< get_style()
			<< " />" << endl;
	}
};

class CompositeFigure : public Shape {
	list<Shape*> content_;
	
	public:
	
	~CompositeFigure() {
		for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) {
			delete *it;
		}
	}

	void add(Shape* shape) {
		content_.push_back(shape);
	}
	
	void draw() const {
		for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) {
			(*it)->draw();
		}
	}
};

class Canvas : public Drawable {
	
	CompositeFigure content_;
	int width_, height_;
	
	public:
	
	Canvas(int width=100, int height=100) : width_(width), height_(height) {}
	
	void add(Shape* shape) {
		content_.add(shape);
	}
	
	void draw() const {
		cout << "<svg width=\"" << width_ 
			<< "\" height=\"" << height_
			<< "\">" << endl;
		content_.draw();
		cout << "</svg>" << endl;
	}
};

enum Direction {
	UP = 1,
	RIGHT = 1 << 1,
	DOWN = 1 << 2,
	LEFT = 1 << 3
};

class MazeError {};

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
	
	Cell(int row, int col) : visited_(false), row_(row), col_(col), 
	walls_(UP | RIGHT | DOWN | LEFT),
	up_(0), right_(0), down_(0), left_(0) {}
	
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
	
	Path* draw(int length) const {
		Path* cell_path = new Path();

		cell_path->set_property("fill", "none");
		cell_path->set_property("stroke", "green");
		cell_path->set_property("stroke-width", "3");
		cell_path->add(Path::Option("M", col_*length));
		cell_path->add(Path::Option("", row_*length));
		if(has_wall(DOWN))
		{
			cell_path->add(Path::Option("L", (col_*length) + length));
			cell_path->add(Path::Option("", row_*length));
		}
		else
		{
			cell_path->add(Path::Option("M", (col_*length) + length));
			cell_path->add(Path::Option("", row_*length));
		}

		if(has_wall(RIGHT))
		{
			cell_path->add(Path::Option("L", (col_*length) + length));
			cell_path->add(Path::Option("", (row_*length) + length));
		}
		else
		{
			cell_path->add(Path::Option("M", (col_*length) + length));
			cell_path->add(Path::Option("", (row_*length) + length));
		}

		if(has_wall(UP))
		{
			cell_path->add(Path::Option("L", col_*length));
			cell_path->add(Path::Option("", (row_*length) + length));
		}
		else
		{
			cell_path->add(Path::Option("M", col_*length));
			cell_path->add(Path::Option("", (row_*length) + length));
		}

		if(has_wall(LEFT))
		{
			cell_path->add(Path::Option("L", col_*length));
			cell_path->add(Path::Option("", row_*length));
		}
		else
		{
			cell_path->add(Path::Option("M", col_*length));
			cell_path->add(Path::Option("", row_*length));
		}

		return cell_path;
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
	
	void draw(int size = 10) const {

		Canvas canvas(cols_*size, rows_*size);

		for(vector<Cell>::const_iterator it = cells_.begin(); it!=cells_.end(); ++it) 
		{
		
			canvas.add(it->draw(size));	
		}

		canvas.draw();
	
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


int main() 
{
	Board board(42, 42);
	Cell& start = board.at(0, 0);
	generate_maze(board, start);
	board.draw(42);
	return 0;
}