# Variables
SRC_DIR = src
BIN_DIR = bin
DOMAIN_CLASSES = $(SRC_DIR)/DomainClasses
DRIVERS = $(SRC_DIR)/Drivers

# Compiler and flags
JAVAC = javac
JFLAGS = -d $(BIN_DIR)

# Source files
DOMAIN_CLASSES_SRC = $(DOMAIN_CLASSES)/BagController.java \
					$(DOMAIN_CLASSES)/Bag.java \
					$(DOMAIN_CLASSES)/Letter.java \
					$(DOMAIN_CLASSES)/Rack.java \
					$(DOMAIN_CLASSES)/DictionaryController.java \
					$(DOMAIN_CLASSES)/Dictionary.java \
					$(DOMAIN_CLASSES)/Board.java \
					$(DOMAIN_CLASSES)/Box.java \
					$(DOMAIN_CLASSES)/Dawg.java

BOARD_DRIVER_SRC = $(DRIVERS)/BoardBoxDriver.java
BAG_DRIVER_SRC = $(DRIVERS)/BagDriver.java
RACK_DRIVER_SRC = $(DRIVERS)/RackDriver.java
DICTIONARY_CONTROLLER_DRIVER_SRC = $(DRIVERS)/DictionaryControllerDriver.java

# Targets
# Compilar todos los drivers
all: BagDriver RackDriver DictionaryControllerDriver

# Compilar los drivers individualmente
BoardDriver: $(DOMAIN_CLASSES_SRC) $(BOARD_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(BOARD_DRIVER_SRC)

BagDriver: $(DOMAIN_CLASSES_SRC) $(BAG_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(BAG_DRIVER_SRC)

RackDriver: $(DOMAIN_CLASSES_SRC) $(RACK_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(RACK_DRIVER_SRC)

DictionaryControllerDriver: $(DOMAIN_CLASSES_SRC) $(DICTIONARY_CONTROLLER_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(DICTIONARY_CONTROLLER_DRIVER_SRC)

# Compilar solo las clases de DomainClasses
DomainClasses: $(BIN_DIR) $(DOMAIN_CLASSES_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC)

# Clean up compiled files

clean:
	rm -rf $(BIN_DIR)/*.class