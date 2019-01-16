#include "pch.h"
#include "poly.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;
using std::string;
using std::stringstream;

void printStudentId() {
	cout << "ASSIGNMENT GROUP 20" << endl;
	cout << "s3678436, s3678436@rmit.edu.vn, Khoa, Le" << endl;
	cout << "s3574935, s3574935@rmit.edu.vn, Thuan, Uong" << endl;
	cout << "s3574915, s3574915@rmit.edu.vn, Minh, Nguyen" << endl;
}

void checkNumInputs(int argc, char *argv[]) { //TEST
	bool check1 = true, check2 = true;
	if (argc == 2) {
		check1 = strcmp(argv[1], "0");
		check2 = strcmp(argv[1], "1");
		if (check1 == 0) {
			printStudentId();
			exit(0);
		}
		if (check1 != 0 && check2 != 0) {
			cout << "Invalid input arguments" << endl;
			exit(0);
		}
	}
	else if (argc != 2) {
		cout << "Invalid number of input arguments" << endl;
		exit(0);
	}
}

int main(int argc, char *argv[])
{
	checkNumInputs(argc, argv);
	poly poly1, poly2, result;
	cout << "Enter the first polynomial: ";
	cin >> poly1;
	cout << "Enter the seccond polynomial: ";
	cin >> poly2;
	poly1.checkPolynomial(poly1, poly2);
	result = poly1 / poly2;
	cout << result << endl;
}
