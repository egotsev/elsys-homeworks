#ifndef MAZE_PATH_H
#define MAZE_PATH_H

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
    
    Styleable& set_property(string key, string value) {
        styles_.insert(pair<string, string>(key, value));
        return *this;
    }
    
    string get_style() const {
        string result = "";
        for(map<string, string>::const_iterator it = styles_.begin(); it != styles_.end(); it++) 					{
            result += it->first + "=\"" + it->second + "\" ";
        }
        return result;
    }
};

class Shape : public Drawable, public Styleable {
};

class Circle : public Shape {
    int radius_;
    Point center_;
    
    public:
    
    Circle(Point center, int radius): center_(center), radius_(radius) {}
    
    void draw() const {
        cout << "<circle cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" r=\"" << radius_ << "\" "
            << get_style()
            << "/>"
            << endl;
    }
};

class Path : public Shape {
    std::map<string, Point*> options_;

public:
    
    Path() {}
    ~Path() {
    	for(map<string, Point*>::iterator it = options_.begin(); it != options_.end(); it++) {
    		delete it->second;
    	}
    }
    
    string get_options() const {
       string result = "";
       for(std::map<string, Point*>::const_iterator it = options_.begin(); it != options_.end(); it++) {
        		result += it->first + "" + std::to_string(((it->second)->Point::get_x())) + " " + std::to_string(((it->second)->Point::get_y())) + " ";
    	 }
    	 return result;
    }
    
    Path& add_option(string prefix, Point* point) {
        options_.insert(pair<string,Point*>(prefix,point));
    }
    
    void draw() const {
        cout << "<path d=\"";
        
				cout << get_options();
        cout << "\" "
            << get_style()
            << " />" << endl;
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

class Canvas : public Drawable {
    
    CompositeFigure content_;
    int width_, height_;
    
    public:
    
    Canvas(int width=300, int height=300) : width_(width), height_(height) {}
    
    void add(Shape* shape) {
        content_.add(shape);
    }
    
    int get_height() {
    	return height_;
    }
    
    int get_width() {
    	return width_;
    }
    
    void draw() const {
        cout << "<svg width=\"" << width_ 
            << "\" height=\"" << height_
            << "\">" << endl;
        content_.draw();
        cout << "</svg>" << endl;
    }
    
};

#endif
