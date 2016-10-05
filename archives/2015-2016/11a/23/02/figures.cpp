#include "figures.hpp"

Point::Point(int x, int y) : x_(x), y_(y) {}

inline int Point::get_x() const {
	return x_;
}

inline int Point::get_y() const {
	return y_;
}


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


Circle::Circle(Point center, int radius) : center_(center), radius_(radius) {}

void Circle::draw() const {
	cout << "<circle cx=\"" << center_.get_x()
		<< "\" cy=\"" << center_.get_y()
		<< "\" r=\"" << radius_ << "\" "
		<< get_style()
		<< "/>"
		<< endl;
}


Path& Path::add_option(string prefix, int number) {
	options_.push_back(Path::Option(prefix, number));
	return *this;
}

void Path::draw() const {
	cout << "<path d=\"";

	for(list<Option>::const_iterator it = options_.begin(); it != options_.end(); it++){
		cout << it->get_prefix() << it->get_number() << " ";
	}
	cout << "\" "
		<< get_style()
		<< "/>" << endl;
}

Path::Option::Option(string prefix, int number)
	: prefix_(prefix), number_(number)
	{}


inline string Path::Option::get_prefix() const {
	return prefix_;
}

void Path::Option::set_prefix(string val) {
	prefix_ = val;
}

int Path::Option::get_number() const {
	return number_;
}

void Path::Option::set_number(int val) {
	number_ = val;
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


Canvas::Canvas(int width, int height) : width_(width), height_(height) {}

void Canvas::add(Shape* shape) {
	content_.add(shape);
}

void Canvas::draw() const {
	cout << "<svg width=\"" << width_ 
	<< "\" height=\"" << height_
	<< "\">" << endl;
	content_.draw();
	cout << "</svg>" << endl;
}  


