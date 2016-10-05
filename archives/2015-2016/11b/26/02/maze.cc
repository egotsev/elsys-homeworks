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
        } else if (next == left()) {
            drill(LEFT);
            next->drill(RIGHT);
        } else {
            throw MazeError();
        }
    }

    bool has_wall(Direction dir) const {
        return walls_ & dir;
    }

    void draw(Canvas& canvas, int length) {
        if (has_wall(UP)) {
            canvas.add(new Line(Point(col_ * length, row_ * length),
                                Point((col_ + 1) * length, row_ * length)));
        }

        if (has_wall(DOWN)) {
            canvas.add(new Line(Point(col_ * length, (row_ + 1) * length),
                                Point((col_ + 1) * length, (row_ + 1) * length)));
        }

        if (has_wall(LEFT)) {
            canvas.add(new Line(Point(col_ * length, row_ * length),
                                Point(col_ * length, (row_ + 1) * length)));
        }

        if (has_wall(RIGHT)) {
            canvas.add(new Line(Point((col_ + 1) * length, row_ * length),
                                Point((col_ + 1) * length, (row_ + 1) * length)));
        }
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
    friend class Cell;

    vector<Cell> cells_;
    int rows_, cols_;
    Canvas canvas_;

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

        Canvas canvas_(void);
    }

    Cell& at(int i, int j) {
        return cells_[i * cols_ + j];
    }

    void draw(int size = 10) {
		for(vector<Cell>::iterator it = cells_.begin();
			it!=cells_.end(); ++it) {

			(*it).draw(canvas_, size);
		}

        canvas_.draw();
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
    Board board(30, 30);
    Cell& start = board.at(0, 0);
    generate_maze(board, start);
    board.draw(20);
    return 0;
}
