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
#include <vector>

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
    virtual ~Shape() {}
};

class Circle : public Shape {
    Point center_;
    int radius_;
    
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
	Point center_;
	Point radius_;
public:
	Ellipse(const Point& center, const Point& radius) : center_(center), radius_(radius) {}
	Ellipse(const Point& center, const int& radius_x, const int& radius_y) : center_(center), radius_(radius_x,radius_y) {}

	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x()
			<< "\" cy=\"" << center_.get_y()
			<< "\" rx=\"" << radius_.get_x()
			<< "\" ry=\"" << radius_.get_y() << "\" />" << endl;
	}
};

class Rectangle : public Shape {
	Point pos_;
	Point size_;
public:
	Rectangle(const Point& pos, const Point& size) : pos_(pos), size_(size) {}
	Rectangle(const Point& pos, const int& width, const int& height) : pos_(pos), size_(width, height) {}

	void draw() const {
		cout << "<rect x=\"" << pos_.get_x() << "\" y=\"" << pos_.get_y()
		<< "\" width=\"" << size_.get_x() << "\" height=\"" << size_.get_y() << "\" />" << endl;
	}
};

class Line : public Shape {
	Point p1_, p2_;
public:
	Line(const Point& p1, const Point& p2) : p1_(p1), p2_(p2) {}
	Line(const int& x1, const int& y1, const int& x2, const int& y2) : p1_(x1, y1), p2_(x2, y2) {}

	void draw() const {
		cout << "<line x1=\"" << p1_.get_x() << "\" y1=\"" << p1_.get_y()
		<< "\" x2=\"" << p2_.get_x() << "\" y2=\"" << p2_.get_y() << "\" />" << endl;
	}
};

class Polygon : public Shape {
	vector<Point> points_;
public:
	Polygon(const vector<Point>& c) : points_(c) {}

	void draw() const {
		cout << "<polygon points=\"";
		for(auto& p : points_) {
			cout << p.get_x() << "," << p.get_y() << " ";
		}
		cout << "\" />" << endl;
	}
};

class Polyline : public Shape {
	vector<Point> points_;
public:
	Polyline(const vector<Point>& c) : points_(c) {}

	void draw() const {
		cout << "<polyline points=\"";
		for(auto& p : points_) {
			cout << p.get_x() << "," << p.get_y() << " ";
		}
		cout << "\" />" << endl;
	}
};

class Path : public Shape {
	vector<string> commands_;
public:
	Path(const vector<string>& commands) : commands_(commands) {}

	void draw() const {
		cout << "<path d=\"";
		for(auto& i : commands_) {
			cout << i << ' ';
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
    c.add(new Ellipse(Point(50, 180), 50, 20));
    c.add(new Rectangle(Point(50, 230), 100, 50));
    c.add(new Line(0, 50, 300, 150));
    c.add(new Polygon({ Point(10, 350), Point(30, 300), Point(80, 330) }));
    c.add(new Polyline({ Point(200, 30), Point(250, 80), Point(280, 10), Point(300, 100) }));
    c.draw();
    return 0;
}