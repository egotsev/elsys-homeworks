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
        for(map<string, string>::const_iterator it = styles_.begin(); it != styles_.end(); it++) {
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
public:
    class Option {
        string prefix_;
        int number_;

    public:
        Option(string prefix, int number)
        : prefix_(prefix), number_(number)
        {}

        string get_prefix() const{
            return prefix_;
        }

        void set_prefix(string val){
            prefix_ = val;
        }

        int get_number() const{
            return number_;
        }

        void set_number(int val){
            number_ = val;
        }
    };

private:
    list<Option> options_;

public:
    
    Path(list<Option> options)
    : options_(options) {}
    
    Path() {}

    Path& add_option(string prefix, int number){
    	options_.push_back(Path::Option(prefix, number));
        return *this;
    }
    
    void draw() const {
        cout << "<path d=\"";

        for(list<Option>::const_iterator it = options_.begin(); it != options_.end(); it++){
            cout << it->get_prefix() << it->get_number() << " ";
        }
        cout << "\" "
            << get_style()
            << "/>" << endl;
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
    
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}
    
    void add(Shape* shape) {
        content_.add(shape);
    }
    
    void draw() const {
        cout << "<svg width=\"" << width_ 
            << "\" height=\"" << height_
            << "\">" << endl;
        content_.draw();
        cout << "</svg>" << endl;
    }  
};

/*int main(){
	Canvas c(2000, 2000);

	Path* p = new Path();
	p->add_option("M", 600);
	p->add_option("", 600);
	p->add_option("l", 600);
	p->add_option("", 600);
	p->set_property("stroke-width", "3");
	p->set_property("stroke", "purple");
	p->set_property("fill", "none");
	c.add(p);

	c.draw();
	return 0;
}*/