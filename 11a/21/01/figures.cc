#include <iostream>
#include <list>

using namespace std;

class Point {
    int x_, y_;
    
    public:
    
    Point(int x=0, int y=0)
    : x_(x), y_(y) {}
    
    int get_x() const {
        return x_;
    }
    
    int get_y() const {
        return y_;
    }
};

class Shape {
    public:
    
    virtual void draw() const=0;
};

class Circle : public Shape {
    int radius_;
    Point center_;
    
    public:
    
    Circle(Point center, int radius)
    : center_(center), radius_(radius) {}
    
    void draw() const {
        cout << "<circle cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" r=\"" << radius_ << "\" />"
            << endl;
    }
};

class Ellipse : public Shape {
    int x_radius_;
    int y_radius_;
    Point center_;
    
    public:
    
    Ellipse(Point center, int x_radius, int y_radius)
    : center_(center), x_radius_(x_radius), y_radius_(y_radius) {}
    
    void draw() const {
        cout << "<ellipse cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" rx=\"" << x_radius_
            << "\" ry=\"" << y_radius_ << "\" />"
            << endl;
    }
};

class Rectangle : public Shape {
    int width_;
    int height_;
    Point position_;
    
    public:
    
    Rectangle(Point position, int width, int height)
    : position_(position), width_(width), height_(height) {}
    
    void draw() const {
        cout << "<rect x=\"" << position_.get_x()
            << "\" y=\"" << position_.get_y()
            << "\" width=\"" << width_
            << "\" height=\"" << height_ << "\" />"
            << endl;
    }
};

class Line : public Shape {
    Point start_;
    Point end_;
    int width_;
    
    public:
    
    Line(Point start, Point end, int width = 2)
    : start_(start), end_(end), width_(width) {}
    
    void draw() const {
        cout << "<line x1=\"" << start_.get_x()
            << "\" y1=\"" << start_.get_y()
            << "\" x2=\"" << end_.get_x()
            << "\" y2=\"" << end_.get_y()
            << "\" style='stroke:black; stroke-width:" << width_
            << "' />"
            << endl;
    }
};

class Polygon : public Shape {
    list<Point> points_;
    
    public:
    
    Polygon(list<Point> points)
    : points_(points) {}
    
    void draw() const {
        cout << "<polygon points=\"";

        for(list<Point>::const_iterator it = points_.begin(); it != points_.end(); it++){
            cout << it->get_x() << "," << it->get_y() << " ";
        }

        cout << "\" />" << endl;
    }
};

class Polyline : public Shape {
    list<Point> points_;
    int width_;
    
    public:
    
    Polyline(list<Point> points, int width = 2)
    : points_(points), width_(width) {}
    
    void draw() const {
        cout << "<polyline points=\"";

        for(list<Point>::const_iterator it = points_.begin(); it != points_.end(); it++){
            cout << it->get_x() << "," << it->get_y() << " ";
        }

        cout << "\" style=\"stroke-width:" << width_
        << ";fill:none;stroke:black\"/>" << endl;
    }
};

class Path : public Shape {
public:
    class Option{
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
    
    void draw() const {
        cout << "<path d=\"";

        for(list<Option>::const_iterator it = options_.begin(); it != options_.end(); it++){
            cout << it->get_prefix() << it->get_number() << " ";
        }

        cout << "\" />" << endl;
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

class Canvas : public CompositeFigure {
    
    int width_, height_;
    
    public:
    
    Canvas(int width=100, int height=100)
    : width_(width), height_(height) {}
    
    void draw() const {
        cout << "<svg width=\"" << width_ 
            << "\" height=\"" << height_
            << "\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }
    
};


int main() {
    Canvas c(400, 600);

    list<Path::Option> options;
    options.push_back(Path::Option("M", 150));
    options.push_back(Path::Option("", 0));
    options.push_back(Path::Option("L", 75));
    options.push_back(Path::Option("", 200));
    options.push_back(Path::Option("L", 250));
    options.push_back(Path::Option("", 200));
    options.push_back(Path::Option("Z", 200));
    Path* p = new Path(options);
    c.add(p);

    c.draw();
    return 0;
}
