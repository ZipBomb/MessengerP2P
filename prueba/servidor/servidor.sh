#!/bin/bash
orbd -ORBInitialPort 6666&
./server.py -ORBDefaultInitRef corbaloc::@localhost:2809
