This project is about sorting images based on human preferences.

It requires a folder called assets which contains the images that are to be ranked.

The are two modes.

The first mode is a single elimination tournament. Once am image is 'beaten' by the other image, it is removed from the pool. 
Comparisons continue until there is one image left which is the winner.

The second mode is involves creating a ranking using the Bradley Terry method with a very high learning rate.
There is a seeding phase where each image is involves in at lesat one comarison.
After that, a Swiss style selection occurs. A random image is selected and it is compared against one of its neighbours (in terms of score).
This ends whenever the number of pairs with a probability between 0.4 and 0.6 is below a threshold which is generally 5% of the images involved.
At the very end, each image is associated with a rank.
