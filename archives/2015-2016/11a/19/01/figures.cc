/*
    Homework:
    Implement the following figures:
    1. Ellipse ok
    2. Rectangle ok
    3. Line ok
    4. Polygon ok
    5. Polyline ok
    6. Path ok
    Deadline: 16.02.2016 17:00
    Place: https://github.com/egotsev/elsys-homeworks/11a/{number_in_class}/01
*/

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

class Line: public Shape {
    Point start_;
    Point end_;

    Line(Point start, Point end): start_(start), end_(end) { }
    void draw(){
        cout << "<line x1=\""<< start_.get_x()
        <<"\" y1=\""<< start_.get_y()
        <<"\" x2=\""<< end_.get_x()
        <<"\" y2=\""<< end_.get_y()
        <<"\" style=\"stroke:rgb(0,0,0);stroke-width:2\" /> \n";
    }
};

class Path: public Shape {
    string instructions_;

    Path(string instructions): instructions_(instructions) { }
    void draw(){
        cout << "<path d=\""<< instructions_
        <<"\" />";
    }
};

class Polyline: public Shape {
    list<Point*> points_;

public:
    Polyline(list<Point*> points): points_(points){}

    void draw() const {
        cout << "<polyline points=\"";
        for (list<Point*>::const_iterator it = points_.begin(); it != points_.end(); it++)
        {
            cout << (*it)->get_x() << "," << (*it)->get_y() << " ";
        }
        cout << "\" style=\"fill:none;stroke:black;stroke-width:3\" />";
    }
}; 

class Polygon: public Shape {
    list<Point*> points_;

public:
    Polygon(list<Point*> points): points_(points){}

    void draw() const {
        cout << "<polygon points=\"";
        for (list<Point*>::const_iterator it = points_.begin(); it != points_.end(); it++)
        {
            cout << (*it)->get_x() << "," << (*it)->get_y() << " ";
        }
        cout << "\" />";
    }
}; 

class Rectangle: public Shape {
    Point center_;
    int height_;
    int width_;

    Rectangle(Point center, int width, int height): center_(center), height_(height), width_(width) { }

    void draw() const{
        cout << "<rect x=\""<< center_.get_x()
        <<"\" y=\""<< center_.get_y() 
        <<"\" width=\""<< width_ 
        <<"\" height=\""<< height_<<"\">";
    }
};

class Ellipse: public Shape {
    Point center_;
    int radius_x_;
    int radius_y_;

    Ellipse(Point center, int radius_x, int radius_y): center_(center), radius_x_(radius_x), radius_y_(radius_y) { }

    void draw() const {
        cout << "<ellipse cx=\""<< center_.get_x()
        << "\" cy=\""<< center_.get_y()
        << "\" rx=\""<< radius_x_
        <<"\" ry=\""<< radius_y_
        <<"\" />";
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
    Canvas c(400, 600);
    c.add(new Circle(Point(20, 20), 15));
    c.add(new Circle(Point(50, 100), 50));
    c.draw();
    return 0;
}