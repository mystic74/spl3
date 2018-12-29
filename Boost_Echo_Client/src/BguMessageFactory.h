#ifndef __BGUMESSAGEFACTORY_H_INCL__
#define __BGUMESSAGEFACTORY_H_INCL__

#include <string>
#include <stdint.h>
#include "src/BGS/ProtocolDataStructs.h"
/**
 * TODO: Add class description
 * 
 * @author   tom
 */
class BguMessageFactory {
public:
    // Method to fetch singleton instance.
    static BguMessageFactory* getInstance();

    // Destructor
    virtual ~BguMessageFactory();

    static bguHeader* getMessage(std::string strLine);
protected:
    // Constructor - protected so users cannot call it.
    BguMessageFactory();

private:
    // private static member referencing the single instance of the object
    // TODO: Replace BguMessageFactory with the type of object you want to
    // return the single instance of
    static BguMessageFactory *_instance;

    // Copy constructor
    // Declared but not defined to prevent auto-generated
    // copy constructor.  Refer to "Effective C++" by Meyers
    BguMessageFactory(const BguMessageFactory& src);

    // Assignment operator
    // Declared but not defined to prevent auto-generated
    // assignment operator.  Refer to "Effective C++" by Meyers
    BguMessageFactory& operator = (const BguMessageFactory& src);

};

// Constructor implementation
inline BguMessageFactory::BguMessageFactory() {
}

// Destructor implementation
inline BguMessageFactory::~BguMessageFactory() {
}

// TODO: Uncomment the copy constructor when you need it.
//inline BguMessageFactory::BguMessageFactory(const BguMessageFactory& src)
//{
//   // TODO: copy
//}

// TODO: Uncomment the assignment operator when you need it.
//inline BguMessageFactory& BguMessageFactory::operator=(const BguMessageFactory& rhs)
//{
//   if (this == &rhs) {
//      return *this;
//   }
//
//   // TODO: assignment
//
//   return *this;
//}

#endif // __BGUMESSAGEFACTORY_H_INCL__
