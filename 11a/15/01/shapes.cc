#include <iostream>
#include <list>

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

class Shape {
    public:
    
    virtual void draw() const=0;
};

class Circle : public Shape {
    int radius_;
    Point center_;
    
    public:
    
    Circle(Point center, int radius): center_(center), radius_(radius) {}
    
    void draw() const {
        cout << "<circle cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" r=\"" << radius_ << "\" />"
            << endl;
    }
};

class Ellipse : public Shape {
    Point radius_;
    Point center_;

public:

    Ellipse(Point center, Point radius) : center_(center), radius_(radius) {}

    void draw() const {
        cout << "<ellipse cx=\"" << center_.get_x() 
        << "\" cy=\"" << center_.get_y()
        << "\" rx= \"" << radius_.get_x()
        << "\" ry=\"" << radius_.get_y() 
        << "\" />"
        << endl;
    }
};

class Recatangle : public Shape {
    Point a_;
    Point b_;

public:

    Recatangle(Point a, Point b) : a_(a), b_(b) {}

    void draw() const {
        cout << "<rect width=\"" << a_.get_x()
        << "\" height=\"" << a_.get_y()
        << "\" style=\"fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)\" />"
        << endl;
    }
};

class Line : public Shape {
    Point begin_;
    Point end_;

public:

    Line(Point begin, Point end) : begin_(begin), end_(end) {}

    void draw() const {
        cout << "<line cx=\"" << begin_.get_x()
        << "\" cy=\"" << begin_.get_y()
        << "\" x2=\"" << end_.get_x()
        << "\" y2=\"" << end_.get_y() 
        << "\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />"
        << endl;
    }
};



class Polygon : public Shape {
    Point a_;
    Point b_;
    Point c_;

public:

    Polygon(Point a, Point b, Point c) : a_(a), b_(b), c_(c) {}

    void draw() const {
        cout << "<polygon points=\"" << a_.get_x() << "," << a_.get_y() << " " << b_.get_x() << "," << b_.get_y() << " " << c_.get_x() << c_.get_y()
        //<< "\" style=\"fill:lime;stroke:purple;stroke-width:1\" << 
        << "\" />"
        << endl;
    }
};

class Polyline : public Shape {
    Point a_;
    Point b_;
    Point c_;
    Point d_;
    Point e_;

public: 

    Polyline(Point a, Point b, Point c, Point d, Point e) : a_(a), b_(b), c_(c), d_(d), e_(e) {}

    void draw() const {
        cout << "<polyline points=\"" << a_.get_x() << "," << a_.get_y() << " " << b_.get_x() << "," << b_.get_y() << " " 
        << c_.get_x() << "," << c_.get_y() << " " << d_.get_x() << "," << d_.get_y() << " " << e_.get_x() << "," << e_.get_y()
        //<< "\" style=\"fill:none;stroke:black;stroke-width:3\" 
        << "\" />"
        << endl;
    }
};

class Path : public Shape {
    string enter_;

public:

    Path(string enter) : enter_(enter) {}

    void draw() const {
        cout << "<path d = \"" << enter_ 
        << "\" />" 
        << endl;
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
    
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}
    
    void draw() const {
        cout << "<svg width=\"" << width_ 
            << "\" height=\"" << height_
            << "\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }
    
};

int main() {
    Canvas c(1000, 1000);
    
    c.add(new Circle(Point(20, 20), 15));
    
    c.add(new Circle(Point(50, 100), 50));

    c.add(new Ellipse(Point(300, 100), Point(80, 40)));

    c.add(new Line(Point(400, 50), Point(600, 100)));

    c.add(new Recatangle(Point(400, 50), Point(100, 50)));    
    
    c.add(new Path("M150 0 L75 200 L225 200 Z"));
    
    c.add(new Polygon(Point(300, 10), Point(270, 100), Point(200, 20)));
    
    c.add(new Polyline(Point(20,20), Point(40,25), Point(60,40), Point(80,120), Point(120,140)));
    
    c.draw();

    return 0;
}