#include <iostream>
#include <list>
#include <string>
#include <sstream>

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

class Circle: public Shape {
    Point center_;
    int r_;

    public:
    Circle(Point center, int r)
        : center_(center),
        r_(r)
    {}

    void draw() const {
        cout << "<circle cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" r=\"" << r_
            << "\" " << get_style()
            << "/>" << endl;
    }
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

class Line : public Shape {
    Point p1_;
    Point p2_;

    public:
    Line(Point p1, Point p2)
        : p1_(p1), p2_(p2)
    {}

    void draw() const {
        cout << "<line x1=\"" << p1_.get_x()
            << "\" y1=\"" << p1_.get_y()
            << "\" x2=\"" << p2_.get_x()
            << "\" y2=\"" << p2_.get_y()
            << "\" " << get_style()
            << "/>" << endl;
    }
};

class Rectangle : public Shape {
    Point start_;
    int width_;
    int height_;

    public:
    Rectangle(Point start, int width, int height)
        : start_(start), width_(width), height_(height)
    {}

    void draw() const {
        cout << "<rect x=\"" << start_.get_x()
            << "\" y=\"" << start_.get_y()
            << "\" width=\"" << width_
            << "\" height=\"" << height_
            << "\" " << get_style()
            << "/>" << endl;
    }
};

class Ellipse : public Shape {
    Point center_;
    int rx_;
    int ry_;

    public:
    Ellipse(Point center, int radius_x, int radius_y)
        : center_(center), rx_(radius_x), ry_(radius_y)
    {}

    void draw() const {
        cout << "<ellipse cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" rx=\"" << rx_
            << "\" ry=\"" << ry_
            << "\" " << get_style()
            << "/>" << endl;
    }
};

class Polygon : public Shape {
    list<Point> points_;

    public:
    Polygon(list<Point> points)
        : points_(points)
    {}

    void add_point(Point point) {
        points_.push_back(point);
    }

    void draw() const {
        ostringstream points;

        for (list<Point>::const_iterator it = points_.begin(), end = points_.end();
                it != end; ++it) {
            points << (*it).get_x() << "," << (*it).get_y() << " ";
        }

        cout << "<polygon points=\"" << points.str()
            << "\" " << get_style()
            << "/>" << endl;
    }
};

class Polyline : public Shape {
    list<Point> points_;

    public:
    Polyline(list<Point> points)
        : points_(points)
    {}

    void add_point(Point point) {
        points_.push_back(point);
    }

    void draw() const {
        ostringstream points;

        for (list<Point>::const_iterator it = points_.begin(),
                end = points_.end(); it != end; ++it) {
            points << (*it).get_x() << "," << (*it).get_y() << " ";
        }

        cout << "<polyline points=\"" << points.str()
            << "\" " << get_style()
            << "/>" << endl;
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

int main() {
    Drawing d(800, 600);

    Circle* c = new Circle(Point(1, 1), 5);
    Line* ln = new Line(Point(1, 1), Point(2, 3));
    Rectangle *rect = new Rectangle(Point(10, 10), 50, 10);
    Ellipse *e = new Ellipse(Point(8, 10), 5, 10);

    d.add(c);
    d.add(ln);
    d.add(rect);
    d.add(e);

    const Point pts[] = { Point(1, 1), Point(5, 6), Point(7, 8) };
    list<Point> points(pts, pts + sizeof(pts) / sizeof(pts[0]));
    Polyline *pl = new Polyline(points);
    Polygon *pg = new Polygon(points);

    d.add(pl);
    d.add(pg);

    const Path::Command cmds[] = {
        Path::Command('m', Point(10, 20)),
        Path::Command('l', Point(5, 6))
    };
    list<Path::Command> commands(cmds, cmds + sizeof(cmds) / sizeof(cmds[0]));
    Path* p = new Path(commands);

    d.add(p);
    d.draw();
}
