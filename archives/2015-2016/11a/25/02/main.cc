#include "iostream"
#include "cstdlib"
#include "cstdio"

#include "maze.cc"

using namespace std;

class Maze{
	int width_, height_;
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
	Maze(int width, int height): width_(width), height_(height){
		path_.add(Path::Option("M", 50));
	    path_.add(Path::Option("", 50));
        path_.set_property("stroke", "blue").set_property("fill", "none").set_property("stroke-width", "2");
	}

	void draw(){
		Canvas canvas(width_, height_);
		Board board(width_, height_);
	    Cell& start = board.at(0, 0);
	    generate_maze(board, start);
	    board.draw(width_, height_, &path_);
	    canvas.add(new Path(path_));
	    canvas.draw();
	}
};

int main(){
	Maze maze(300, 300);
	maze.draw();

	return 0;
}