#include <iostream>
#include <list>
#include <string>
#include <map>

using namespace std;

class Point {
    int x_, y_;

    public:

    Point(int=0, int=0);

    int get_x() const;
    int get_y() const;
};

class Drawable {
    public:
    
    virtual void draw() const=0;
    virtual ~Drawable() {} // Non virtual destructor of a base class causes warnings / is a bad habit
};

class Styleable {
    map<string, string> styles_;
    
    public:
    
    Styleable& set_property(string, string);
    string get_style() const;
    virtual ~Styleable() {}
};

class Shape : public Drawable, public Styleable {
public:
    virtual ~Shape() {}
};

class Circle : public Shape {
    Point center_;
    int radius_;
    
    public:
    
    Circle(Point, int);
    void draw() const;
};

class Path : public Shape {
public:
	class Option;

private:
    list<Option> options_;

public:

	Path(list<Option> options) : options_(options) {}
	Path() {}

	Path& add_option(string, int);
	void draw() const;
};

class Path::Option {
	string prefix_;
	int number_;
	
public:
	Option(string, int);

	string get_prefix() const;
	void set_prefix(string);

	int get_number() const;
	void set_number(int val);
};


class CompositeFigure : public Shape {
	list<Shape*> content_;

	public:

	~CompositeFigure();

    void add(Shape*);
    
    void draw() const;
};

class Canvas : public Drawable {
    
    CompositeFigure content_;
    int width_, height_;
    
    public:
    
    Canvas(int=100, int=100);
    
    void add(Shape*);
    
    void draw() const;
};
