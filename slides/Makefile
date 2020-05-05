
SRC_DIR=source
DEST_DIR=build
REVEAL=reveal.js-3.9.2

SRC_FILES1 = $(wildcard $(SRC_DIR)/*.mds)
DEST_FILES1 = $(patsubst $(SRC_DIR)/%.mds,$(DEST_DIR)/%.html,$(SRC_FILES1))

all: $(DEST_FILES1)
	@echo "Done"

$(DEST_DIR)/%.html : $(SRC_DIR)/%.mds
	@if [ ! -d build/reveal.js ]; then \
		tar xzf $(REVEAL).tar.gz; \
		mkdir -p $(DEST_DIR); \
		cp -r $(REVEAL) $(DEST_DIR)/reveal.js; \
		rm -rf $(REVEAL); \
		echo "reveal.js copied to $(DEST_DIR) directory"; \
	fi;
	pandoc --standalone --section-divs --variable theme="simple" --from=markdown --variable \
transition="linear" --variable keyboard="{37:'prev',38:'prev',39:'next',40:'next'}" \
-t revealjs -s -o $@ $< 

clean:
	rm -rf build

# variable keyboard is overriden so that up and down work in reveal.js as left and right 
# to avoid issues with some presenters sending only up and down codes

# revelation mkstatic $< --output-folder build --output-file $@ --force

