/*
 * Messanger.h
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */

#ifndef TCP_MESSANGER_H_
#define TCP_MESSANGER_H_

#include "TCPCommunication.h"

class Messanger {
public:
	Messanger(TCPCommunication &);
	virtual ~Messanger();
};

#endif /* TCP_MESSANGER_H_ */
