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
	list<Point> point_list_;
public:
	Polygon(list<Point> point_list):point_list_(point_list)
	{}

	void start_draw(Point p) const{
		cout << p.get_x() << "," << p.get_y() << " "; 
	}

	void draw() const{
		cout << "<polygon points=\"";
		 for (list<Point>::const_iterator it = point_list_.begin(); it != point_list_.end(); it++) {
            start_draw(*it);
        }
		cout << "\" style=\"fill:lime;stroke:purple;stroke-width:1\" />" << endl;
	}
};

class Polyline: public Shape{
	list<Point> point_list_;
public:
	Polyline(list<Point> point_list):point_list_(point_list)
	{}

	void start_draw(Point p) const{
		cout << p.get_x() << "," << p.get_y() << " "; 
	}

	void draw() const{
		cout << "<polyline points=\"";
		 for (list<Point>::const_iterator it = point_list_.begin(); it != point_list_.end(); it++) {
            start_draw(*it);
         }
		cout << "\" style=\"fill:none;stroke:black;stroke-width:3\" />" << endl;
	}

};

class Path: public Shape{
	string path_;
public:
	Path(string path):path_(path)
	{}

	void draw() const{
		cout << "<path d=\"" << path_ <<  "\" />" 
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
	list<Point> list_of_points_polygone;
	list<Point>::iterator it = list_of_points_polygone.begin();
	list_of_points_polygone.insert(it, Point(600, 100));
	list_of_points_polygone.insert(it, Point(700, 100));
	list_of_points_polygone.insert(it, Point(800, 300));
	list_of_points_polygone.insert(it, Point(900, 350));

	list<Point> list_of_points_polyline;
	list<Point>::iterator itLine = list_of_points_polyline.begin();
	list_of_points_polygone.insert(itLine, Point(500, 500));
	list_of_points_polygone.insert(itLine, Point(500, 700));
	list_of_points_polygone.insert(itLine, Point(900, 300));
	list_of_points_polygone.insert(itLine, Point(900, 350));

	string path_string = "M150 0 L75 200 L225 200 Z";

    Canvas c(1000, 1000);
  	c.add(new Circle(Point(20, 20), 15));
   	c.add(new Circle(Point(50, 100), 50));
   	c.add(new Ellipse(Point(200, 200), 100, 50));
    c.add(new Rectangle(Point(200, 400),100, 50));
    c.add(new Line(Point(0, 300), Point(300, 300)));
   	c.add(new Polygon(list_of_points_polygone));
    c.add(new Polyline(list_of_points_polyline));
    c.add(new Path(path_string));
    c.draw();
    return 0;
}