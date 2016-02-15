/*
    Homework:
    Implement the following figures:
    1. Ellipse
    2. Rectangle
    3. Line
    4. Polygon
    5. Polyline
    6. Path
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

class Ellipse : public Shape{
	int radius_x_;
	int radius_y_;
	Point center_;

public:
	Ellipse(Point center, int radius_x, int radius_y):center_(center),radius_x_(radius_x),radius_y_(radius_y)
	{}

	void draw() const{
		cout <<  "<ellipse cx=\"" << center_.get_x() 
		<< "\" cy=\"" << center_.get_y() << "\" rx=\"" 
		<< radius_x_ << "\" ry=\"" << radius_y_ 
		<< "\" style=\"fill:yellow;stroke:purple;stroke-width:2\"/>" 
		<< endl;
	}
};

class Rectangle : public Shape{
	Point center_;
	int width_;
	int height_;
public:
	Rectangle(Point center, int width, int height):center_(center), width_(width), height_(height)
	{}

	void draw() const{
		cout << "<rect x=\"" << center_.get_x() << 
		"\" y=\"" << center_.get_y() << "\" width=\"" 
		<< width_ << "\" height=\"" << height_ << 
		"\" style=\"fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)\"/>" 
		<< endl;
	}
};

class Line : public Shape{
	Point start_point_;
	Point end_point_;

public:
	Line(Point start, Point end):start_point_(start), end_point_(end){}

	void draw() const{
		cout << "<line x1=\"" << start_point_.get_x() 
		<< "\" y1=\"" << start_point_.get_y() << "\" x2=\"" 
		<< end_point_.get_x() << "\" y2=\"" << end_point_.get_y() 
		<< "\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />" << endl;
	}
};

class Polygon : public Shape{
	Point first_point_;
	Point second_point_;
	Point third_point_;
public:
	Polygon(Point first, Point second, Point third):first_point_(first),second_point_(second),third_point_(third)
	{}

	void draw() const{
		cout << "<polygon points=\"" << first_point_.get_x() 
		<< "," << first_point_.get_y() << " " << second_point_.get_x() 
		<< "," << second_point_.get_y() << " " << third_point_.get_x() 
		<< "," << third_point_.get_y()  << "\" style=\"fill:lime;stroke:purple;stroke-width:1\" />" 
		<< endl;
	}
};

class Polyline: public Shape{
	Point first_;
	Point second_;
	Point third_;
public:
	Polyline(Point first, Point second, Point third):first_(first),second_(second),third_(third)
	{}

	void draw() const{
		cout << "<polyline points=\"" << first_.get_x() << "," << first_.get_y() 
		<< " " << second_.get_x() << "," << second_.get_y() 
		<< " " << third_.get_x() << "," << third_.get_y() 
		<< "\" style=\"fill:none;stroke:purple;stroke-width:3\" />" 
		<< endl;
	}
};

class Path: public Shape{
	Point first_;
	Point second_;
	Point third_;
public:
	Path(Point first, Point second, Point third):first_(first),second_(second),third_(third)
	{}

	void draw() const{
		cout << "<path d=\"M" << first_.get_x() << " " << first_.get_y() 
		<< " L" << second_.get_x() << " " << second_.get_y() 
		<< " L" << third_.get_x() << " " << third_.get_y() 
		<< "Z\" />" 
		<< endl;
	}
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
    c.add(new Ellipse(Point(200, 200), 100, 50));
    c.add(new Rectangle(Point(200, 400),100, 50));
    c.add(new Line(Point(0, 300), Point(300, 300)));
    c.add(new Polygon(Point(100, 600),Point(100, 700),Point(300, 800)));
    c.add(new Polyline(Point(500, 100),Point(600, 700),Point(400, 800)));
    c.add(new Path(Point(700, 100),Point(600, 200),Point(800, 200)));
    c.draw();
    return 0;
}