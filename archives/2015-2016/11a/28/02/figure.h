#ifndef FIGURE_H
#define FIGURE_H
#include <iostream>
#include <list>
#include <string>
#include <map>

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

class Drawable {
    public:
    
    virtual void draw() const=0;
};

class Styleable {
    map<string, string> styles_;
    
    public:
    
    Styleable& set_property(string key, string value);
     
    string get_style() const;
};

class Shape : public Drawable, public Styleable {
};

class CompositeFigure : public Shape {
    list<Shape*> content_;
    
    public:
    
    ~CompositeFigure(); 
    
    void add(Shape* shape); 
    
    void draw() const;
};

class Line : public Shape
{
	Point from_;
	Point to_;
public:
	Line(Point from, Point to): from_(from), to_(to) {}
	void draw() const;

};

class Canvas : public Drawable {
    
    CompositeFigure content_;
    int width_, height_;
    
    public:
    
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}
    
    void add(Shape* shape);
    
    void draw() const;
};
#endif