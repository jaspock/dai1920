# Minimal makefile for Sphinx documentation
#

# You can set these variables from the command line, and also
# from the environment for the first two.
SPHINXOPTS    ?=
SPHINXBUILD   ?= sphinx-build
SOURCEDIR     = source
BUILDDIR      = build

SLIDESDIR = source/_static/slides

.PHONY: help Makefile clean push pages slides

# Put it first so that "make" without argument is like "make help".
help:
	@$(SPHINXBUILD) -M help "$(SOURCEDIR)" "$(BUILDDIR)" $(SPHINXOPTS) $(O)

# Catch-all target: route all unknown targets to Sphinx using the new
# "make mode" option.  $(O) is meant as a shortcut for $(SPHINXOPTS).
%: Makefile
	@if [ ! -d $(SLIDESDIR) ]; then \
		echo "directory $(SLIDESDIR) not found!"; \
		echo "run make slides first"; \
		exit 1; \
	fi
	@$(SPHINXBUILD) -M $@ "$(SOURCEDIR)" "$(BUILDDIR)" $(SPHINXOPTS) $(O)

slides:
	@if [ ! -d ../slides/build ]; then \
		echo "directory ../slides/build not found!"; \
	else \
		rm -rf $(SLIDESDIR); \
		mkdir $(SLIDESDIR); \
		cp -r ../slides/build/* $(SLIDESDIR); \
		echo "slides copied to $(SLIDESDIR)"; \
	fi;

pages:
	rm -rf /tmp/gh-pages
	cp -r $(BUILDDIR)/html /tmp/gh-pages
	touch /tmp/gh-pages/.nojekyll
	git checkout gh-pages
	cd .. && rm -rf * && cp -r /tmp/gh-pages/* . && git add . && \
	git commit -m "Website en gh-pages actualizado" && git push origin gh-pages && git checkout master
	git branch

clean:
	rm -rf build $(SLIDESDIR)

