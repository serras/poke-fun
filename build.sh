mdbook build
cd slides
npm run build -- --out "../docs/slides" --base "/poke-fun/slides" --router-mode hash --download
cd ..
