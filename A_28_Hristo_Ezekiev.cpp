#include <iostream>
#include <list>
using namespace std;
class Point 
{
    int x_, y_;
public:
    Point(int x=0, int y=0) : x_(x), y_(y) {}
    int get_x() const 
    {
        return x_;
    }
    int get_y() const 
    {
        return y_;
    }
};
class Shape 
{
public:
    virtual void draw() const=0;
};

class Circle : public Shape 
{
    int radius_;
    Point center_;
public:
    Circle(Point center, int radius): center_(center), radius_(radius) {}
    void draw() const 
    {
        cout << "<circle cx=\"" << center_.get_x()
        << "\" cy=\"" << center_.get_y()
        << "\" r=\"" << radius_ << "\" />"
        << endl;
    }
};

class Rect : public Shape
{
    int width_, height_;
    Point place_;
public:
    Rect(Point place, int width, int height): place_(place), width_(width), height_(height) {}
    void draw() const
    {
        cout << "<rect x=\"" << place_.get_x()
        << "\" y=\"" << place_.get_y()
        << "\" width=\"" << width_  
        << "\" height=\"" << height_ 
        << "\" />" << endl;
    }
};

class Ellipse : public Shape
{
	int width_, height_;
	Point center_;
public:
	Ellipse(Point center, int width, int height): center_(center), width_(width), height_(height) {}
    void draw() const
    {
        cout << "<ellipse cx=\"" << center_.get_x()
        << "\" cy=\"" << center_.get_y()
        << "\" rx=\"" << width_  
        << "\" ry=\"" << height_ 
        << "\" />" << endl;
    }
};

class Polygon : public Shape
{
	Point p1_, p2_, p3_, p4_;
	//Point place_;
public:
	Polygon(Point p1, Point p2, Point p3, Point p4): p1_(p1), p2_(p2), p3_(p3), p4_(p4) {}
	void draw() const
	{
		cout << "<polygon points=\"" << p1_.get_x()
		<< "," << p1_.get_y() << " " << p2_.get_x() 
		<< "," << p2_.get_y() << " " << p3_.get_x() 
		<< "," << p3_.get_y() << " " << p4_.get_x() 
		<< "," << p4_.get_y() << "\" />" << endl; 
	}
};

class Polyline : public Shape
{
	Point p1_, p2_, p3_, p4_, p5_, p6_;
	//Point place_;
public:
	Polyline(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6): p1_(p1), p2_(p2), p3_(p3), p4_(p4), p5_(p5), p6_(p6) {}
	void draw() const
	{
		cout << "<polyline points=\"" << p1_.get_x()
		<< "," << p1_.get_y() << " " << p2_.get_x() 
		<< "," << p2_.get_y() << " " << p3_.get_x() 
		<< "," << p3_.get_y() << " " << p4_.get_x() 
		<< "," << p4_.get_y() << " " << p5_.get_x()
		<< "," << p5_.get_y() << " " << p6_.get_x()
		<< "," << p6_.get_y() << "\" style=\"fill:none;stroke:black;stroke-width:4\" />" << endl; 
	}
};

class Line : public Shape
{
	Point from_;
	Point to_;
public:
	Line(Point from, Point to): from_(from), to_(to) {}
	void draw() const
	{
		cout << "<line x1=\"" << from_.get_x()
		<< "\" y1=\"" << from_.get_y()
		<< "\" x2=\"" << to_.get_x()
		<< "\" y2=\"" << to_.get_y()
		<< "\" style=\"stroke:rgb(0,0,0);stroke-width:4\"/>" << endl;
	}
};

class Path : public Shape
{
	//int char start_, co1_, co2_, end_;
	Point from_, through_, to_;
public:
	Path(Point from, Point through, Point to): from_(from), through_(through), to_(to) {}
	
	void draw() const
	{
		cout << "<path d=\"" << "M" << from_.get_x()
		<< " " << from_.get_y() << " " << "L" <<
		through_.get_x() << " " << through_.get_y() <<
		" " << "L" << to_.get_x() << " " << to_.get_y()
		<< " " << "Z" << "\"/>" << endl;
	}
};

class CompositeFigure : public Shape 
{
    list<Shape*> content_;
public:
    ~CompositeFigure() 
    {
        for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) 
        {
            delete *it;
        }
    }

    void add(Shape* shape) 
    {
        content_.push_back(shape);
    }

    void draw() const 
    {
        for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) 
        {
            (*it)->draw();
        }
    }
};

class Canvas : public CompositeFigure 
{
    int width_, height_;
public:
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}
    void draw() const 
    {
        cout << "<svg width=\"" << width_
        << "\" height=\"" << height_
        << "\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }
};

int main() 
{
    Canvas c(400, 600);
    c.add(new Circle(Point(270, 420), 15));
    c.add(new Circle(Point(300, 95), 50));
    c.add(new Rect(Point(240, 150), 100, 30));
	c.add(new Ellipse(Point(309, 300), 50, 100));
	c.add(new Line(Point(17,80), Point(163,250)));
	c.add(new Polygon(Point(220,100), Point(30,20), Point(17,50), Point(163,234))); 
	c.add(new Polyline(Point(0, 200), Point(40, 200), Point(40, 240), Point(80, 240), Point(80, 280), Point(140, 300)));   
	c.add(new Path(Point(100, 350), Point(75, 520), Point(225, 500)));	
c.draw();
    return 0;
}
