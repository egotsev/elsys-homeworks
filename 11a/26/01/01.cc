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
#include <string>

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
    int radius_1;
    int radius_2;
    Point center_;

    public:

    Ellipse(Point center, int radius1, int radius2): center_(center), radius_1(radius1), radius_2(radius2) {}

    void draw() const {
        cout << "<ellipse cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" rx=\"" << radius_1
            << "\" ry=\"" << radius_2 << "\" />"
        << endl;
    }
};

class Rectangle : public Shape {
    int x_;
    int y_;
    int width_;
    int height_;

    public:

    Rectangle(int x, int y, int width, int height): x_(x), y_(y), width_(width), height_(height) {}

    void draw() const {
        cout << "<rect x=\""<< x_ << "\" y=\""<< y_ << "\" width=\"" << width_
            << "\" height=\"" << height_ << "\"style=\"fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)\" />"
        << endl;
    }
};

class Line : public Shape {
    Point dot_1;
    Point dot_2;

    public:

    Line(Point dot1, Point dot2): dot_1(dot1), dot_2(dot2) {}

    void draw() const {
        cout << "<line x1=\"" << dot_1.get_x()
            << "\" y1=\"" << dot_1.get_y()
            << "\" x2=\"" << dot_2.get_x()
            << "\" y2=\"" << dot_2.get_y()
            << "\" style=\"stroke:rgb(255,0,0);stroke-width:3\"/>"
        << endl;
    }
};

class Polygon : public Shape {
	list<Point> cont_;

	public:

	Polygon(list<Point> cont) : cont_(cont) {}

	void draw() const {
		cout << "<polygon points=\"";
		for(list<Point>::const_iterator it = cont_.begin(); it != cont_.end(); it++) {
			cout << (*it).get_x() << "," << (*it).get_y() << " ";
		}
		cout <<"\" />" << endl;
	}

};


class Polyline : public Shape {
	list<Point> cont_;

	public:

	Polyline(list<Point> cont) : cont_(cont) {}

	void draw() const {
		cout << "<polyline points=\"";
		for(list<Point>::const_iterator it = cont_.begin(); it != cont_.end(); it++) {
			cout << (*it).get_x() << "," << (*it).get_y() << " ";
		}
		cout <<"\" style=\"fill:none;stroke:blue;stroke-width:2\" />" << endl;
	}

};

class Path : public Shape {
	string str_;

	public:

	Path(string str) : str_(str) {}

	void draw() const {
		cout << "<path d=\"" << str_ << "\" />" << endl;
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

    Canvas c(600, 700);

    c.add(new Circle(Point(20, 20), 15));
    c.add(new Ellipse(Point(60, 110), 50, 90));
    c.add(new Rectangle(80, 150, 300, 150));
    c.add(new Line(Point(40,50), Point(60,100)));

    list<Point> polygon;
	polygon.push_back(Point(200,200));
	polygon.push_back(Point(300,300));
	polygon.push_back(Point(400,500));
	polygon.push_back(Point(600,200));
    c.add(new Polygon(polygon));

	list<Point> polyline;
	polyline.push_back(Point(100,200));
	polyline.push_back(Point(150,350));
	polyline.push_back(Point(300,450));
	polyline.push_back(Point(350,470));
	polyline.push_back(Point(500,100));
	c.add(new Polyline(polyline));

	c.add(new Path("M130 0 L66 100 L225 35 Z"));

    c.draw();

    // Oh, well, let't just call it "modern art"...

    return 0;
}
