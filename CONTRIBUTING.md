### CONTRIBUTING

Thanks for stopping by. You are awesome!

__All__ contributions are welcome: bug reports, patches, documentation (!) and everything you can think of.


If and only if you want to contribute code to this projects you have to be aware of the fact, that this project is using [Project Lombok](http://projectlombok.org/) so your IDE should support this.

Please send PR only to the `master` branch.

### Submitting issues

You may open an issue [here](https://github.com/achtern/AchternEngine/issues/new), but be sure to follow the guidlines below:

* If you encountered a bug, please include the very first log statement with DEBUG log level turned on for the CoreEngine,
it should look something like this:

```
21:05:11.462 [main] DEBUG o.a.AchternEngine.core.CoreEngine - AchternEngine v0.4;
Build on 27.07.2015 @ 20:38:54 MESZ by Christian Gärtner git commit = 0.4
```

* Be descriptive!
* That's all for now. Thanks for helping out!

### Pull Requests

* Follow the [coding style](#coding-style) of this project and use markdown when ever possible.
* Start the PR with a one line describing the changes.
* Add the three provided (below) tables which allows for standardized PRs.
* Include some screenshots, if there are any visible changes in the graphics engine!
* _Try_ to add javadoc to new classes/methods
* _Try_ to have unit test coverage for new stuff
* You can find an example PR [here](https://gist.github.com/ChristianGaertner/5dd3568eae450c83ed0c), you can view the raw code and copy paste!

### Coding Style

This projects follows [Google's Java Style Guide](https://google.github.io/styleguide/javaguide.html) loosely.

If you can try to use lombok as much as possible. If you want to add JavaDoc to generated getters and setters have read
[here](https://projectlombok.org/features/GetterSetter.html).

For static/final inner lombok `@Data` classes please follow this convention:

```java
final @Data class Foo {}
```


### git branching model

This project used to be developed in the develop branch and this branch was merged into the master branch for each release.
A typical [git flow](http://nvie.com/posts/a-successful-git-branching-model/) one might say.

Recently I have read [this article](http://endoflineblog.com/gitflow-considered-harmful) which shows why git flow isn't the best
solution; and I have to say that I agree with the author.

The master branch does __NOT provide any value__ to the history. Every release is tagged and checking out the latest stable
release is now for example `git checkout 0.4` instead of `git checkout master`. For this even more clear and verbose than
the git flow approach, isn't it.

Beside dropping the develop branch this project follows git flow to some degree. Feature branches are still a thing and
might be on remote as well. Please do not consider these as official "achtern" branches. They are subject to frequent changes
and might be gone just days after release.

If you want to send pull request please __DO NOT__ rebase your feature branch, I'd like to keep the development process
in the projects history (if you want to merge hundreds of tiny commits this policy might not apply though...).