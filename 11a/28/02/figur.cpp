#include "figure.h"

Styleable& Styleable::set_property(string key, string value) {
    styles_.insert(pair<string, string>(key, value));
    return *this;
}

string Styleable::get_style() const {
    string result = "";
    for(map<string, string>::const_iterator it = styles_.begin(); it != styles_.end(); it++) {
        result += it->first + "=\"" + it->second + "\" ";
    }
    return result;
}

CompositeFigure::~CompositeFigure() {
    for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) {
        delete *it;
    }
}

void CompositeFigure::add(Shape* shape) {
    content_.push_back(shape);
}

void CompositeFigure::draw() const {
    for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) {
        (*it)->draw();
    }
}

void Line::draw() const
{
    cout << "<line x1=\"" << from_.get_x()
    << "\" y1=\"" << from_.get_y()
    << "\" x2=\"" << to_.get_x()
    << "\" y2=\"" << to_.get_y()
    << "\" " << get_style() << "/>" << endl;
}

void Canvas::add(Shape* shape) {
    content_.add(shape);
}

void Canvas::draw() const {
    cout << "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" << width_ 
        << "\" height=\"" << height_
        << "\">" << endl;
    content_.draw();
    cout << "</svg>" << endl;
}