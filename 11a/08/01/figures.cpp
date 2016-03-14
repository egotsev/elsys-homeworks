/*
	1. Ellipse - done
    2. Rectangle - done
    3. Line - done
    4. Polygon - done
    5. Polyline
    6. Path 

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

class Ellipse : public Shape  {
	Point center_;
	int rad1_;
	int rad2_;

public:
	Ellipse(Point center, int rad1, int rad2) : center_(center), rad1_(rad1), rad2_(rad2) {}

	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x()
		<< "\" cy=\"" << center_.get_y()
		<< "\" rx=\"" << rad1_
		<< "\" ry=\"" << rad2_ << "\" />"
		<< endl;
	}
 
};

class Rectangle : public Shape {
	Point center_;
	int a_;
	int b_;

public:
	Rectangle(Point center, int a, int b) : center_(center), a_(a), b_(b) {}

	void draw() const {
		cout << "<rect x=\"" << center_.get_x()
		<< "\" y=\"" << center_.get_y()
		<< "\" width=\"" << a_
		<< "\" height=\"" << b_ << "\" />"
		<< endl;
	}

};

class Line : public Shape {
	Point begin_;
	Point end_;

public:
	Line(Point begin, Point end) : begin_(begin), end_(end) {}

	void draw() const {
		cout << "<line x1=\"" << begin_.get_x()
		<< "\" y1=\"" << begin_.get_y() 
		<< "\" x2=\"" << end_.get_x()
		<< "\" y2=\"" << end_.get_y() 
		<< "\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />"
		<< endl;
	}
	
};

class Polygon : public Shape {
	Point first_;
	Point second_;
	Point third_;
public:
	Polygon(Point first, Point second, Point third) : first_(first), second_(second), third_(third) {}

	void draw() const {
		cout << "<polygon points=\"" << first_.get_x()
		<< "," << first_.get_y()
		<< " " << second_.get_x()
		<< "," << second_.get_y()
		<< " " << third_.get_x()
		<< "," << third_.get_y() 
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
    Canvas c(400, 600);
    c.add(new Circle(Point(20, 20), 15));
 	
	c.add(new Ellipse(Point(50,130),20,30));
	
	c.add(new Rectangle(Point(50,200),40,40));	

	c.add(new Line(Point(200,250),Point(70,70))); 

	c.add(new Polygon(Point(300,200),Point(300,150),Point(100,90)));	
	c.draw();
   
	return 0;
}
