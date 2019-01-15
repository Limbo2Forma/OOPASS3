#include "pch.h"
#include "poly.h"

using std::string;
using std::stringstream;
using std::endl;
using std::cout;
using std::cin;
using std::showpos;
using std::noshowpos;

istream &operator >> (istream &get, poly &p) {
	getline(get, p.input);
	p.sstr.str("");
	p.sstr << p.input;
	p.input = "";
	while (!p.sstr.eof()) {
		p.sstr >> p.output;
		p.input = p.input + p.output;
		p.output = "";
	}
	return get;
}

ostream &operator << (ostream &print, poly &p) {
	print << endl << endl << "Method 1: Dynamic Array" << endl;
	p.printResultMethod1(p);
	print << endl << endl << "Method 2: LinkedList" << endl;
	p.printResultMethod2(p);
	return print;
}

poly &poly::operator = (const poly &p) {
	if (this == &p) {
		return *this;
	}
	else {
		sizeArr = p.sizeArr;
		sizeRemainder = p.sizeRemainder;
		polyCoeff = new double[sizeArr];
		polyDegree = new double[sizeArr];
		sub_polyCoeff = new double[sizeRemainder];
		sub_polyDegree = new double[sizeRemainder];

		for (int i = 0; i < sizeArr; i++) {
			polyCoeff[i] = p.polyCoeff[i];
			polyDegree[i] = p.polyDegree[i];
		}
		for (int j = 0; j < sizeRemainder; j++) {
			sub_polyCoeff[j] = p.sub_polyCoeff[j];
			sub_polyDegree[j] = p.sub_polyDegree[j];
		}

		headNode = new PolyLinkedlist;
		PolyLinkedlist *currentQuotient = headNode;
		currentQuotient->setNext(NULL);
		PolyLinkedlist *tempQuotient = p.headNode;

		while (tempQuotient != NULL) {
			currentQuotient->setCoeff(tempQuotient->getCoeff());
			currentQuotient->setDeg(tempQuotient->getDeg());
			tempQuotient = tempQuotient->getNext();
			if (tempQuotient != NULL) {
				currentQuotient = addNode(currentQuotient);
			}
		}

		sub_headNode = new PolyLinkedlist;
		PolyLinkedlist *currentRemainder = sub_headNode;
		currentRemainder->setNext(NULL);
		PolyLinkedlist *tempRemainder = p.sub_headNode;

		while (tempRemainder != NULL) {
			currentRemainder->setCoeff(tempRemainder->getCoeff());
			currentRemainder->setDeg(tempRemainder->getDeg());
			tempRemainder = tempRemainder->getNext();
			if (tempRemainder != NULL) {
				currentRemainder = addNode(currentRemainder);
			}
		}
		return *this;
	}

}

poly::poly(const poly &p)
{
	*this = p;
}

poly poly::operator / (poly &divisor) {
	poly result;

	double leadDegree = sub_polyDegree[0] - divisor.sub_polyDegree[0];
	double power = 0.0;
	int i = 0, m = 0, z = 0;
	bool nonzero = false;
	//#############################################################################################################################################
	//Dynamic array method
	result.polyCoeff = new double[(int)(sub_polyDegree[0] - divisor.sub_polyDegree[0] + 2)];
	result.polyDegree = new double[(int)(leadDegree + 1)];
	result.sub_polyCoeff = new double[(int)sub_polyDegree[0]];
	result.sub_polyDegree = new double[(int)sub_polyDegree[0]];

	while ((sub_polyDegree[m] >= divisor.sub_polyDegree[0]) && sub_polyCoeff[m] != 0) {
		if (i == 0) {
			result.polyCoeff[i] = sub_polyCoeff[i] / divisor.sub_polyCoeff[i];
			result.polyDegree[i] = sub_polyDegree[i] - divisor.sub_polyDegree[i];
		}
		for (int j = 0; j < divisor.sizeArr; j++) {
			power = result.polyDegree[i] + divisor.sub_polyDegree[j];
			for (int k = 0; k < sizeArr; k++) {
				if (sub_polyDegree[k] == power) {
					sub_polyCoeff[k] = (float)sub_polyCoeff[k] - (float)(result.polyCoeff[i] * divisor.sub_polyCoeff[j]);
					break;
				}
			}
		}
		for (int n = 0; n < sizeArr; n++) {
			if (sub_polyCoeff[n] != 0) {
				result.polyCoeff[i + 1] = sub_polyCoeff[n] / divisor.sub_polyCoeff[0];
				result.polyDegree[i + 1] = sub_polyDegree[n] - divisor.sub_polyDegree[0];
				m = n;
				break;
			}
		}
		i++;
	}
	for (int m = 0; m < i; m++) {
		result.sizeArr++;
	}
	for (int l = 0; l < sizeArr; l++) {
		if (sub_polyCoeff[l] != 0) {
			result.sub_polyCoeff[z] = sub_polyCoeff[l];
			result.sub_polyDegree[z] = sub_polyDegree[l];
			z++;
			result.sizeRemainder++;
			nonzero = true;
		}
	}
	if (!nonzero) {
		result.sub_polyCoeff[z] = 0;
		result.sizeRemainder++;
	}

	//#############################################################################################################################################
	// Linked list method
	PolyLinkedlist *dividendPtr, *divisorPtr;
	dividendPtr = headNode;
	divisorPtr = divisor.headNode;

	result.headNode = new PolyLinkedlist;
	PolyLinkedlist *currentResult = result.headNode;
	currentResult->setNext(NULL);

	result.sub_headNode = new PolyLinkedlist;
	PolyLinkedlist *current_sub_Result = result.sub_headNode;
	current_sub_Result->setNext(NULL);

	while ((dividendPtr != NULL && divisorPtr != NULL) && (dividendPtr->getDeg() >= divisorPtr->getDeg()) && dividendPtr->getCoeff() != 0) {
		currentResult->setCoeff(dividendPtr->getCoeff() / divisorPtr->getCoeff());
		currentResult->setDeg(dividendPtr->getDeg() - divisorPtr->getDeg());
		while (divisorPtr != NULL) {
			power = currentResult->getDeg() + divisorPtr->getDeg();
			while (dividendPtr != NULL) {
				if (dividendPtr->getDeg() == power) {
					dividendPtr->setCoeff((float)dividendPtr->getCoeff() - (float)(currentResult->getCoeff() * divisorPtr->getCoeff()));
					dividendPtr = dividendPtr->getNext();
					break;
				}
				else if (dividendPtr->getNext()->getDeg() == power) {
					dividendPtr->getNext()->setCoeff((float)dividendPtr->getNext()->getCoeff() - (float)((currentResult->getCoeff() * divisorPtr->getCoeff())));
					dividendPtr = dividendPtr->getNext();
					break;
				}
				else if (dividendPtr->getDeg() != power) {
					dividendPtr = headNode;
					PolyLinkedlist *temp = headNode;
					while (dividendPtr != NULL) {
						dividendPtr = dividendPtr->getNext();
						if (temp->getDeg() > power && power > dividendPtr->getDeg()) {
							temp->setNext(new PolyLinkedlist);
							temp = temp->getNext();
							temp->setCoeff(0.0 - (currentResult->getCoeff() * divisorPtr->getCoeff()));
							temp->setDeg(power);
							temp->setNext(dividendPtr);
							break;
						}
						else if (temp->getDeg() > power && power == dividendPtr->getDeg()) {
							dividendPtr->setCoeff((float)dividendPtr->getCoeff() - (float)(currentResult->getCoeff() * divisorPtr->getCoeff()));
							break;
						}
						else {
							temp = temp->getNext();
						}
					}
					break;
				}
				dividendPtr = dividendPtr->getNext();
			}
			divisorPtr = divisorPtr->getNext();
		}
		divisorPtr = divisor.headNode;

		if (headNode->getNext() != NULL) {
			if (headNode->getCoeff() == 0.0) {
				headNode = deleteNode(headNode);
				dividendPtr = headNode;
			}
			if (dividendPtr->getDeg() >= divisorPtr->getDeg()) {
				currentResult = addNode(currentResult);
			}
		}
	}

	if (headNode->getCoeff() != 0) {
		while (dividendPtr != NULL) {
			current_sub_Result->setCoeff(dividendPtr->getCoeff());
			current_sub_Result->setDeg(dividendPtr->getDeg());
			dividendPtr = dividendPtr->getNext();
			if (dividendPtr != NULL) {
				current_sub_Result = addNode(current_sub_Result);
			}
		}

	}
	else if (headNode->getCoeff() == 0) {
		current_sub_Result->setCoeff(headNode->getCoeff());
		current_sub_Result->setDeg(headNode->getDeg());
	}
	return result;
}

void poly::checkPolynomial(poly &p1, poly &p2) {
	int j = 0, k = 0;
	checkInputPoly(p1);
	checkInputPoly(p2);
	if (p1.polyDegree[0] < p2.polyDegree[0]) {
		cout << "Dividend polynomial has smaller degree than a degree of divisor polynomial" << endl;
		exit(1);
	}
	//Adding number 0 to the coefficient at the place where there is no degree for computing division
	p1.sizeArr = (int)p1.polyDegree[0] + 1;
	p1.sub_polyDegree = new double[sizeArr];
	p1.sub_polyCoeff = new double[sizeArr];
	for (int i = 0; i <= p1.sizeArr; i++) {
		p1.sub_polyDegree[i] = p1.polyDegree[0] - i;
		if ((p1.polyDegree[j] - p1.sub_polyDegree[i]) == 0) {
			p1.sub_polyCoeff[i] = p1.polyCoeff[j];
			j++;
		}
		else {
			p1.sub_polyCoeff[i] = 0;
		}
	}
	////Remove number 0 out the coefficient of the divisor for computing division
	j = 0;
	for (int i = 0; i < p2.count; i++) {
		if (p2.polyCoeff[i] != 0) {
			p2.sizeArr++;
		} else if (p2.polyCoeff[i] == 0) {
 			k++;
		}
	}
	if (k == p2.count - 1 && p2.count > 1) {
		cout << "The divisor cannot be equal to 0" << endl;
		exit(1);
	}

	p2.sub_polyCoeff = new double[sizeArr];
	p2.sub_polyDegree = new double[sizeArr];
	for (int i = 0; i < p2.count; i++) {
		if (p2.polyCoeff[i] != 0) {
			p2.sub_polyCoeff[j] = p2.polyCoeff[i];
			p2.sub_polyDegree[j] = p2.polyDegree[i];
			j++;
		}
	}
}

void poly::convertCoeff(poly &p, int count) {
	string temp = "", coeff = "";
	int k = 0, m = 0, n = 0;
	int sign_count = 0;
	bool flag = false, flaglinkedlist = false;
	p.polyCoeff = new double[count];	
	p.polyDegree = new double[count];	

	p.headNode = new PolyLinkedlist;
	PolyLinkedlist *current = p.headNode;
	current->setNext(NULL);

	for (unsigned int i = 0; i <= p.input.length(); i++) {
		if (p.input[0] == 'x' && i == 0) {
			coeff = coeff + '1';
		}
		if (p.input[0] == '-' && i == 0) {
			temp = temp + p.input[i];
		}
		if (p.input[i] != '+' && p.input[i] != '-' && p.input[i] != NULL) {
			temp = temp + p.input[i];
		}
		if (p.input[i] == '*') {
			sign_count++;
		}
		if (sign_count > 1) {
			cout << "Invalid term input" << endl;
			exit(1);
		}
		if ((p.input[i] == '+' || p.input[i] == '-' 
			|| p.input[i] == NULL) && i > 0) {
			for (unsigned int j = 0; j <= temp.length(); j++) {
				if (j == 0 && temp[j] == '0' && temp[j + 1] == '*') {
					break;
				}
				if (temp[j] != 'x' && temp[j] != '*' && 
					temp[j] != '^' && temp[j] != NULL) {
					coeff = coeff + temp[j];
				}
				if (temp[j] == 'x' || temp[j] == '*' || 
					temp[j] == NULL || temp[j] == '^') {
					// x only and -x
					
					if ((temp[j] == NULL && temp[j - 1] == 'x') ||
						(j == 1 && temp[j] == 'x')) {
						coeff = coeff + '1';
					}
					// last x/ +x/-x 
					else if (j != 0 && temp[j] == 'x' && (temp[j - 1] == '+' ||
						temp[j - 1] == '-') && temp[j + 1] == NULL) {
						coeff = coeff + '1';
					}
					if (coeff != "") {
						k = stoi(coeff);
						if (!flag && (temp[j] == 'x' || temp[j] == '*' ||
							temp[j] == NULL || p.input[i] == NULL)) {
							p.polyCoeff[m] = k;
							m++;
							flag = true;
							if (k != 0) {
								current->setCoeff(k); //linked list
								flaglinkedlist = true;
							}
							// no x
							if (temp[j] == NULL && temp[j - 1] != 'x') {
								p.polyDegree[n] = 0;
								current->setDeg(0); // linked list
								if (p.input[i] != NULL) {
									flag = false;
									flaglinkedlist = false;
									n++;
								}
							}
						}
						else if (flag) {
							p.polyDegree[n] = k;
							n++;
							flag = false;
							if (flaglinkedlist) {
								current->setDeg(k); //linked list
								if (p.input[i] != NULL) {
									current = addNode(current);
								}
								flaglinkedlist = false;
							}

						}
					}
					coeff = "";
				}
			}
			sign_count = 0;
			temp = "";
			if (p.input[i] != NULL) {
				temp = temp + p.input[i];
			}
		}
	}
}

void poly::checkInputPoly(poly &p) {
	bool valid = false;
	for (unsigned int i = 0; i < p.input.length(); i++) {
		if (!isdigit(p.input[i])) {
			if (p.input[i] != 'x' && p.input[i] != '^' && p.input[i] != '+'
				&& p.input[i] != '-' && p.input[i] != '*') {
				cout << "Input coefficient is not a digit";
				exit(1);
			}
			else if (p.input[i + 1] == p.input[i] ) {
				cout << "Duplicate operators, signs or x variables";
				exit(1);
			}
			else if ((i != 0 && p.input[i] == 'x' && p.input[i - 1] == '^') ||
						(i == 0 && p.input[i] == '^')) {
				cout << "Improper placement of the caret sign '^'";
				exit(1);
			}
			else if (p.input[i] == '^') {
				if ((p.input[i - 1] != 'x' || p.input[i - 1] == 'x') && 
					(!isdigit(p.input[i + 1]) && p.input[i + 1] != 'x')) {
					if (!isdigit(p.input[i + 1]) && p.input[i - 1] == 'x') {
						cout << "Input degree order is not a digit";
						exit(1);
					}
					else {
						cout << "Improper placement of the caret sign '^'";
						exit(1);
					}
				}
				else if ((p.input[i - 1] == '+' || p.input[i - 1] == '-') && i > 0) {
					cout << "Improper placement of the caret sign '^'"; 
					exit(1);
				}
				else if (isdigit(p.input[i - 1]) && isdigit(p.input[i + 1])) {
					cout << "Improper placement of the caret sign '^'";
					exit(1);
				}
			}
			else if (p.input[i] == '*') {
				if (i != 0) {
					if (isdigit(p.input[i - 1]) && isdigit(p.input[i + 1]) || p.input[i - 1] == 'x' 
						|| (p.input[i + 1] == 'x' && !isdigit(p.input[i - 1]))) {
						cout << "Improper placement of the asterisk sign '*'";
						exit(1);
					}
				}
				else {
					if (p.input[i + 1] == 'x') {
						cout << "Improper placement of the asterisk sign '*'";
						exit(1);
					}
				}
			}
			else if (p.input[i] == 'x' &&  p.input[i + 1] == NULL) {
				valid = true;
			}
			if ((i != 0 && p.input[i] == 'x' && (isdigit(p.input[i + 1]) || isdigit(p.input[i - 1])))
				|| (i == 0 && p.input[i] == 'x' && (isdigit(p.input[i + 1])))) {
				cout << "No asterisk sign '*' between the coefficient and 'x'" << endl;
				exit(1);
			}
			if (((p.input[i] == '+' || p.input[i] == '-' || p.input[i] == '*') && p.input[i + 1] == NULL)
				|| ((p.input[i] == '+' || p.input[i] == '-' || p.input[i] == '*')
				&& (p.input[i + 1] == '+' || p.input[i + 1] == '-' || p.input[i + 1] == '*'))) {
				cout << "Invalid sign placement" << endl;
				exit(1);
			}
			if ((p.input[i] == '+' || p.input[i] == '-') && i > 0) {
				p.count++;
			}
		}
		else if (p.input[0] == '0' and p.input[i+1] == NULL) {
			cout << "The divisor cannot be equal to 0" << endl;
			exit(1);
		}
		else {
			valid = true;
		}
	}
	if (valid) {
		convertCoeff(p, p.count);
		for (int i = 0; i < p.count - 1; i++) {
			if ((p.polyDegree[i]) <= (p.polyDegree[i + 1])) {
				cout << "Invalid degree order in the polynomial" << endl;
				exit(1);
			}
		}
	}
}

void poly::printResultMethod1(poly &p) {
	cout << "Quotient: ";
	for (int i = 0; i < p.sizeArr; i++) {
		if (p.polyDegree[i] == 1 && (p.polyCoeff[i] == 1 || p.polyCoeff[i] == -1)) {
			if (i == 0) {
				if (p.polyCoeff[i] == 1) {
					cout << "x";
				}
				else if (p.polyCoeff[i] == -1) {
					cout << "-x";
				}
			}
			else if (i != 0) {
				if (p.polyCoeff[i] == 1) {
					cout << "+x";
				}
				else if (p.polyCoeff[i] == -1) {
					cout << "-x";
				}
			}
		}
		else if (p.polyDegree[i] == 1 && p.polyCoeff[i] != 0 &&
			p.polyCoeff[i] != 1 && p.polyCoeff[i] != -1) {
			if (i == 0) {
				cout << p.polyCoeff[i] << "*x";
			}
			else if (i != 0) {
				cout << showpos << p.polyCoeff[i] << "*x";
			}
		}
		//
		else if (p.polyDegree[i] != 1 && p.polyDegree[i] != 0 && 
			(p.polyCoeff[i] == 1 || p.polyCoeff[i] == -1)) {
			if (i == 0) {
				if (p.polyCoeff[i] == 1) {
					cout << "x^" << noshowpos << p.polyDegree[i];
				}
				else if (p.polyCoeff[i] == -1) {
					cout << "-x^" << noshowpos << p.polyDegree[i];
				}
			}
			else if (i != 0) {
				if (p.polyCoeff[i] == 1) {
					cout << "+x^" << noshowpos << p.polyDegree[i];
				}
				else if (p.polyCoeff[i] == -1) {
					cout << "-x^" << noshowpos << p.polyDegree[i];
				}
			}
		}
		else if (p.polyDegree[i] != 1 && p.polyDegree[i] != 0 && p.polyCoeff[i] != 1) {
			if (i == 0) {
				cout << p.polyCoeff[i] << "*x^" << p.polyDegree[i];
			}
			else if (i != 0) {
				cout << showpos << p.polyCoeff[i] << "*x^" << noshowpos << p.polyDegree[i];
			}
		}
		else if (p.polyDegree[i] == 0 && (p.polyCoeff[i] != 1 ||
			p.polyCoeff[i] == 1) && p.polyCoeff[i] != 0) {
			if (i == 0) {
				cout << p.polyCoeff[i];
			}
			else if (i != 0) {
				cout << showpos << p.polyCoeff[i];
			}
		}
	}

	cout << endl << "Remainder: ";
	for (int j = 0; j < p.sizeRemainder; j++) {
		if (j == 0) {
			if (p.sub_polyCoeff[j] == 0) {
				cout << "0" << endl;
			}
			else if (p.sub_polyDegree[j] == 1) {
				if (p.sub_polyCoeff[j] == 1) {
					cout << "x";
				}
				else if (p.sub_polyCoeff[j] == -1) {
					cout << "-x";
				}
			}
		}
		if (j != 0) {
			if (p.sub_polyDegree[j] == 1) {
				if (p.sub_polyCoeff[j] == 1) {
					cout << "+x";
				}
				else if (p.sub_polyCoeff[j] == -1) {
					cout << "-x";
				}
			}
		}
		if (p.sub_polyDegree[j] == 1 && p.sub_polyCoeff[j] != 0 &&
			p.sub_polyCoeff[j] != 1 && p.sub_polyCoeff[j] != -1) {
			if (j == 0) {
				cout << noshowpos << p.sub_polyCoeff[j] << "*x";
			}
			else if (j != 0) {
				cout << showpos << p.sub_polyCoeff[j] << "*x";
			}
		}
		//
		else if (p.sub_polyDegree[j] != 1 && p.sub_polyDegree[j] != 0 &&
			(p.sub_polyCoeff[j] == 1 || p.sub_polyCoeff[j] == -1)) {
			if (j == 0) {
				if (p.sub_polyCoeff[j] == 1)
					cout << "x^" << noshowpos << p.sub_polyDegree[j];
				else if (p.sub_polyCoeff[j] == -1)
					cout << "-x^" << noshowpos << p.sub_polyDegree[j];
			}
			else if (j != 0) {
				if (p.sub_polyCoeff[j] == 1)
					cout << "+x^" << noshowpos << p.sub_polyDegree[j];
				else if (p.sub_polyCoeff[j] == -1)
					cout << "-x^" << noshowpos << p.sub_polyDegree[j];
			}
		}
		else if (p.sub_polyDegree[j] == 0 && (p.sub_polyCoeff[j] != 1 ||
			p.sub_polyCoeff[j] == 1) && p.sub_polyCoeff[j] != 0) {
			if (j == 0) {
				cout << noshowpos << p.sub_polyCoeff[j];
			}
			else if (j != 0) {
				cout << showpos << p.sub_polyCoeff[j];
			}
		}
		else if (p.sub_polyCoeff[j] != 0 && p.sub_polyDegree[j] != 1 &&
			p.sub_polyDegree[j] != 0 && p.sub_polyCoeff[j] != 1) {
			if (j == 0) {
				cout << noshowpos << p.sub_polyCoeff[j] << "*x^" << noshowpos << p.sub_polyDegree[j];
			}
			else if (j != 0) {
				cout << showpos << p.sub_polyCoeff[j] << "*x^" << noshowpos << p.sub_polyDegree[j];
			}
		}
	}

	delete[] p.polyCoeff;
	delete[] p.polyDegree;
	delete[] p.sub_polyCoeff;
	delete[] p.sub_polyDegree;
}

void poly::printResultMethod2(poly & p) {
	PolyLinkedlist *current = p.headNode;
	PolyLinkedlist *remainder = p.sub_headNode;
	cout << "Quotient: ";
	while (current != NULL) {
		if (current->getDeg() == 1 && (current->getCoeff() == 1
			|| current->getCoeff() == -1)) {
			if (current == p.headNode) {
				if (current->getCoeff() == 1) {
					cout << "x";
				}
				else if (current->getCoeff() == -1) {
					cout << "-x";
				}
			}
			else if (current != p.headNode) {
				if (current->getCoeff() == 1) {
					cout << "+x";
				}
				else if (current->getCoeff() == -1) {
					cout << "-x";
				}
			}
		}
		else if (current->getDeg() == 1 && current->getCoeff() != 0
			&& current->getCoeff() != 1 && current->getCoeff() != -1) {
			if (current == p.headNode) {
				cout << noshowpos << current->getCoeff() << "*x";
			}
			else if (current != p.headNode) {
				cout << showpos << current->getCoeff() << "*x";
			}
		}
		else if (current->getDeg() != 1 && current->getDeg() != 0
			&& (current->getCoeff() == 1 || current->getCoeff() == -1)) {
			if (current == p.headNode) {
				if (current->getCoeff() == 1) {
					cout << "x^" << noshowpos << current->getDeg();
				}
				else if (current->getCoeff() == -1) {
					cout << "-x^" << noshowpos << current->getDeg();
				}
			}
			else if (current != p.headNode) {
				if (current->getCoeff() == 1) {
					cout << "+x^" << noshowpos << current->getDeg();
				}
				else if (current->getCoeff() == -1) {
					cout << "-x^" << noshowpos << current->getDeg();
				}
			}
		}
		else if (current->getDeg() != 1 && current->getDeg() != 0 && current->getCoeff() != 1) {
			if (current == p.headNode) {
				cout << noshowpos << current->getCoeff() << "*x^" << noshowpos << current->getDeg();
			}
			else if (current != p.headNode) {
				cout << showpos << current->getCoeff() << "*x^" << noshowpos << current->getDeg();
			}
		}
		else if (current->getDeg() == 0 && (current->getCoeff() != 1
			|| current->getCoeff() == 1) && current->getCoeff() != 0) {
			if (current == p.headNode) {
				cout << noshowpos << current->getCoeff();
			}
			else if (current != p.headNode) {
				cout << showpos << current->getCoeff();
			}
		}
		current = current->getNext();
	}

	cout << endl << "Remainder: ";
	while (remainder != NULL) {
		if (remainder == p.sub_headNode) {
			if (remainder->getCoeff() == 0) {
				cout << "0" << endl;
			}
			else if (remainder->getDeg() == 1) {
				if (remainder->getCoeff() == 1) {
					cout << "x";
				}
				else if (remainder->getCoeff() == -1) {
					cout << "-x";
				}
			}
		}
		if (remainder != p.sub_headNode) {
			if (remainder->getDeg() == 1) {
				if (remainder->getCoeff() == 1) {
					cout << "+x";
				}
				else if (remainder->getCoeff() == -1) {
					cout << "-x";
				}
			}
		}
		if (remainder->getDeg() == 1 && remainder->getCoeff() != 0 &&
			remainder->getCoeff() != 1 && remainder->getCoeff() != -1) {
			if (remainder == p.sub_headNode) {
				cout << noshowpos << remainder->getCoeff() << "*x";
			}
			else if (remainder != p.sub_headNode) {
				cout << showpos << remainder->getCoeff() << "*x";
			}
		}
		else if (remainder->getDeg() != 1 && remainder->getDeg() != 0 &&
			(remainder->getCoeff() == 1 || remainder->getCoeff() == -1)) {
			if (remainder == p.sub_headNode) {
				if (remainder->getCoeff() == 1)
					cout << "x^" << noshowpos << remainder->getDeg();
				else if (remainder->getCoeff() == -1)
					cout << "-x^" << noshowpos << remainder->getDeg();
			}
			else if (remainder != p.sub_headNode) {
				if (remainder->getCoeff() == 1)
					cout << "+x^" << noshowpos << remainder->getDeg();
				else if (remainder->getCoeff() == -1)
					cout << "-x^" << noshowpos << remainder->getDeg();
			}
		}
		else if (remainder->getDeg() == 0 && (remainder->getCoeff() != 1 ||
			remainder->getCoeff() == 1) && remainder->getCoeff() != 0) {
			if (remainder == p.sub_headNode) {
				cout << noshowpos << remainder->getCoeff();
			}
			else if (remainder != p.sub_headNode) {
				cout << showpos << remainder->getCoeff();
			}
		}
		else if (remainder->getCoeff() != 0 && remainder->getDeg() != 1 &&
			remainder->getDeg() != 0 && remainder->getCoeff() != 1) {
			if (remainder == p.sub_headNode) {
				cout << noshowpos << remainder->getCoeff() << "*x^" << noshowpos << remainder->getDeg();
			}
			else if (remainder != p.sub_headNode) {
				cout << showpos << remainder->getCoeff() << "*x^" << noshowpos << remainder->getDeg();
			}
		}
		remainder = remainder->getNext();
	}


	deleteList(p.headNode);
	deleteList(p.sub_headNode);
}
