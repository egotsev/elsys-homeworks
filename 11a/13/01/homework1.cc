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
    int radiusX_;
    int radiusY_;
    Point center_;
    
    public:
    
    Ellipse(Point center, int radiusX, int radiusY): center_(center), radiusX_(radiusX), radiusY_(radiusY) {}
    
    void draw() const {
        cout << "<ellipse cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" rx=\"" << radiusX_ 
            << "\" ry=\"" << radiusY_
            << "\" />"<< endl;
    }
};

class Rectangle : public Shape {
    int width_;
    int height_;
    Point start_;

    public:
    
    Rectangle(Point start, int width, int height): start_(start), width_(width), height_(height) {}
    
    void draw() const {
        cout << "<rect x=\"" << start_.get_x() 
            << "\" y=\"" << start_.get_y() 
            << "\" width=\"" << width_
            << "\" height=\"" << height_ << "\" />"
            << endl;
    }
};

class Line : public Shape {
    Point start_;
    Point end_;

    public:
    
    Line(Point start, Point end): start_(start), end_(end) {}
    
    void draw() const {
        cout << "<line x1=\"" << start_.get_x() 
            << "\" y1=\"" << start_.get_y() 
            << "\" x2=\"" << end_.get_x() 
            << "\" y2=\"" << end_.get_y()
            << "\" style=\"stroke:rgb(0,255,0);stroke-width:2\" "
            << " />"
            << endl;
            //<line x1="0" y1="0" x2="200" y2="200" style="stroke:rgb(255,0,0);stroke-width:2" />
    }
};

class Polygon : public Shape {
    list<Point> points_;

    public:
    
    Polygon(list<Point> points): points_(points) {}

    void draw() const {
        cout << "<polygon points=\"" ;
            for(list<Point>::const_iterator it = points_.begin(); it != points_.end();it++){
                cout << it->get_x()
                    << ","
                    << it->get_y() 
                    << " " ;
            }  
            cout << "\" style=\"fill:lime;stroke:purple;stroke-width:3\""  
            << "/>" << endl;
    }
};

class Polyline : public Shape {
    list<Point> points_;

    public:
    
    Polyline(list<Point> points): points_(points) {}

    void draw() const {
        cout << "<polyline points=\"" ;
            for(list<Point>::const_iterator it = points_.begin(); it != points_.end();it++){
                cout << it->get_x()
                    << ","
                    << it->get_y() 
                    << " " ;
            }  
            cout << "\" style=\"fill:none;stroke:red;stroke-width:3\""  
            << "/>" << endl;
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
    list<Point> list_line;
    list<Point> list_polygon;   
    c.add(new Circle(Point(20, 20), 15));
    c.add(new Circle(Point(50, 100), 50));
    c.add(new Ellipse(Point(120,120), 40, 20));
    c.add(new Rectangle(Point(180, 180), 160, 100));
    c.add(new Line(Point(40, 50), Point(290, 300)));
    
    list_line.push_back(Point(200,200));
    list_line.push_back(Point(220,240));
    list_line.push_back(Point(255,268)); 
    list_line.push_back(Point(279,267));
    c.add(new Polyline(list_line));

    list_polygon.push_back(Point(400,400));
    list_polygon.push_back(Point(450,350));
    list_polygon.push_back(Point(500,650));
    c.add(new Polygon(list_polygon));
    c.draw();
    return 0;
}
