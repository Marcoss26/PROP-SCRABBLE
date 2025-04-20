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
					$(DOMAIN_CLASSES)/Profile.java \
					$(DOMAIN_CLASSES)/ProfileController.java \
					$(DOMAIN_CLASSES)/Dawg.java

# Driver classes source files
DICTIONARY_CONTROLLER_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/DictionaryController.java \
					$(DOMAIN_CLASSES)/Dictionary.java \
					$(DOMAIN_CLASSES)/Dawg.java

PROFILE_CONTROLLER_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/ProfileController.java \
					$(DOMAIN_CLASSES)/Profile.java

BOARD_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Board.java \
					$(DOMAIN_CLASSES)/Box.java

BAG_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/BagController.java \
					$(DOMAIN_CLASSES)/Letter.java

RACK_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Rack.java \
					$(DOMAIN_CLASSES)/BagController.java \
					$(DOMAIN_CLASSES)/Letter.java

# Driver source files
					
PROFILE_CONTROLLER_DRIVER_SRC = $(DRIVERS)/ProfileControllerDriver.java
BOARD_DRIVER_SRC = $(DRIVERS)/BoardBoxDriver.java
BAG_DRIVER_SRC = $(DRIVERS)/BagDriver.java
RACK_DRIVER_SRC = $(DRIVERS)/RackDriver.java
DICTIONARY_CONTROLLER_DRIVER_SRC = $(DRIVERS)/DictionaryControllerDriver.java

# Targets
# Compilar todos los drivers
all: BagDriver RackDriver DictionaryControllerDriver

# Compilar los drivers individualmente
ProfileControllerDriver: $(PROFILE_CONTROLLER_DRIVER_CLASSES_SRC) $(PROFILE_CONTROLLER_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(PROFILE_CONTROLLER_DRIVER_CLASSES_SRC) $(PROFILE_CONTROLLER_DRIVER_SRC)

BoardDriver: $(BOARD_DRIVER_CLASSES_SRC) $(BOARD_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(BOARD_DRIVER_CLASSES_SRC) $(BOARD_DRIVER_SRC)

BagDriver: $(BAG_DRIVER_CLASSES_SRC) $(BAG_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(BAG_DRIVER_CLASSES_SRC) $(BAG_DRIVER_SRC)

RackDriver: $(RACK_DRIVER_CLASSES_SRC) $(RACK_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(RACK_DRIVER_CLASSES_SRC) $(RACK_DRIVER_SRC)

DictionaryControllerDriver: $(DICTIONARY_CONTROLLER_DRIVER_CLASSES_SRC) $(DICTIONARY_CONTROLLER_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DICTIONARY_CONTROLLER_DRIVER_CLASSES_SRC) $(DICTIONARY_CONTROLLER_DRIVER_SRC)

# Compilar solo las clases de DomainClasses
DomainClasses: $(BIN_DIR) $(DOMAIN_CLASSES_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC)

# Ejecutar drivers
run-BagDriver: BagDriver
	java -cp $(BIN_DIR) Drivers.BagDriver
run-RackDriver: RackDriver
	java -cp $(BIN_DIR) Drivers.RackDriver
run-ProfileControllerDriver: ProfileControllerDriver
	java -cp $(BIN_DIR) Drivers.ProfileControllerDriver
run-BoardDriver: BoardDriver
	java -cp $(BIN_DIR) Drivers.BoardBoxDriver
run-DictionaryControllerDriver: DictionaryControllerDriver
	java -cp $(BIN_DIR) Drivers.DictionaryControllerDriver

# Borrar los archivos compilados

clean:
	rm -rf $(BIN_DIR)/*.class