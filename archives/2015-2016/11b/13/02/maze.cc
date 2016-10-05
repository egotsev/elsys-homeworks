#include <iostream>
#include <list>
#include <string>
#include <sstream>
#include <vector>
#include <cstdlib>

using namespace std;

class Point {
    int x_;
    int y_;

    public:
    Point(int x = 0, int y = 0)
        : x_(x), y_(y)
    {}

    int get_x() const {
        return x_;
    }

    int get_y() const {
        return y_;
    }
};

class Styleable {
    string stroke_;
    string fill_;
    string style_;

    public:
    Styleable()
    {}

    Styleable& set_stroke(string color) {
        stroke_ = color;
        return *this;
    }

    Styleable& set_style(string style) {
        style_ = style;
        return *this;
    }

    string get_style() const {
        string result;
        if(!style_.empty()) {
            result = " style=\"" + style_ + "\" ";
            return result;
        }

        if(!stroke_.empty()) {
            result += " stroke=\"" + stroke_ + "\" ";
        }

        return result;
    }

};

class Shape: public Styleable {
    public:
        virtual void draw() const = 0;
};

class Drawing {
    int width_;
    int height_;
    list<Shape*> shapes_;

    public:
    Drawing(int width, int height)
        : width_(width), height_(height)
    {}

    void add(Shape* shape) {
        shapes_.push_back(shape);
    }

    void draw() const {
        cout << "<svg xmlns=\"http://www.w3.org/2000/svg"
             << "\" width=\"" << width_
             << "\" height=\"" << height_
             << "\">" << endl;

        for (list<Shape*>::const_iterator it = shapes_.begin();
                it!=shapes_.end(); ++it) {
            (*it)->draw();	
        }

        cout << "</svg>" << endl;
    }
};

class Path : public Shape {
    public:
        class Command {
            char action_;
            Point direction_;

            public:
            Command() {}

            Command(char action, Point direction)
                : action_(action), direction_(direction)
            {}

            char get_action() const {
                return action_;
            }

            Point get_direction() const {
                return direction_;
            }
        };

        Path() : commands_(0) {}

        Path(list<Command> commands)
            : commands_(commands)
        {}

        void add_command(Command command) {
            commands_.push_back(command);
        }

        void draw() const {
            ostringstream commands;

            for (list<Command>::const_iterator it = commands_.begin(),
                    end = commands_.end(); it != end; ++it) {
                commands << (*it).get_action()
                    << " " << (*it).get_direction().get_x()
                    << " " << (*it).get_direction().get_y() << " ";
            }

            cout << "<path d=\"" << commands.str()
                << "Z \" " << get_style()
                << "/>" << endl;
        }

    private:
        list<Command> commands_;
};

enum Dir {
    LEFT = 1,
    RIGHT = 1 << 1,
    UP = 1 << 2,
    DOWN = 1 << 3,
};

class BoardError {};

class Cell {
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
    Cell(int row = -1, int col = -1)
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
        if (n == up()) {
            drill(UP);
            n->drill(DOWN);
        } else if (n == down()) {
            drill(DOWN);
            n->drill(UP);
        } else if (n == left()) {
            drill(LEFT);
            n->drill(RIGHT);
        } else if (n == right()) {
            drill(RIGHT);
            n->drill(LEFT);
        } else {
            throw BoardError();
        }
    }

    void draw(int step, Path* path) const {
        Path::Command c1('M', Point(col_ * step, row_ * step));
        path->add_command(c1);

        Point p2(step, 0);
        if (has_wall(DOWN)) {
            path->add_command(Path::Command('l', p2));
        } else {
            path->add_command(Path::Command('m', p2));
        }

        Point p3(0, step);
        if (has_wall(RIGHT)) {
            path->add_command(Path::Command('l', p3));
        } else {
            path->add_command(Path::Command('m', p3));
        }

        Point p4(-step, 0);
        if (has_wall(UP)) {
            path->add_command(Path::Command('l', p4));
        } else {
            path->add_command(Path::Command('m', p4));
        }

        Point p5(0, -step);
        if (has_wall(LEFT)) {
            path->add_command(Path::Command('l', p5));
        } else {
            path->add_command(Path::Command('m', p5));
        }
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
        if (up() != 0 && !up()->visited())
            result.push_back(up());
        if (down() != 0 && !down()->visited())
            result.push_back(down());
        if (left() != 0 && !left()->visited())
            result.push_back(left());
        if (right() != 0 && !right()->visited())
            result.push_back(right());
        return result;
    }

    bool has_unvisited_neighbours() {
        return !(get_unvisited_neighbours().empty());
    }

    Cell* get_random_unvisited_neighbour() {
        if (!has_unvisited_neighbours()) {
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
        for (int i = 0; i < rows_; ++i) {
            for (int j = 0; j < cols_; ++j) {
                cells_.push_back(Cell(i, j));
            }
        }

        for (int i = 0; i < rows_; ++i) {
            for(int j = 0; j < cols_; ++j) {
                Cell& c = at(i, j);
                if (i < rows_ - 1)
                    c.set_up(&at(i + 1, j));
                if (i > 0)
                    c.set_down(&at(i - 1, j));
                if (j > 0)
                    c.set_left(&at(i, j - 1));
                if (j < cols_ - 1)
                    c.set_right(&at(i, j + 1));
            }
        }

    }

    Cell& at(int i, int j) {
        int index = i * cols_ + j;
        return cells_[index];
    }

    const Cell& at(int i, int j) const {
        int index = i * cols_ + j;
        return cells_[index];
    }

    void draw(int size = 10) const {
        Drawing d(800, 600);
        Path *path = new Path();

        for (vector<Cell>::const_iterator it = cells_.begin();
                it != cells_.end(); ++it) {
            (*it).draw(size, path);
        }

        ostringstream style;
        style << "fill: none;"
              << "stroke: black;";

        path->set_style(style.str());
        d.add(path);
        d.draw();
    }
};

void generate(Board& b, Cell& c) {
    c.set_visited();

    while (c.has_unvisited_neighbours()) {
        Cell* next = c.get_random_unvisited_neighbour();
        next->set_visited();
        c.drill_to(next);
        generate(b, *next);
    }
}

int main() {
    Board b(30,30);
    Cell& c = b.at(0,0);

    generate(b, c);
    b.draw(8);

    return 0;
}
