package io.github.achtern.AchternEngine.core.resource.fileparser;

public class CommentRemover implements LineBasedParser {

    protected String commentToken;

    public CommentRemover(String commentToken) {
        this.commentToken = commentToken;
    }

    /**
     * Parses the line.
     * Should NOT add a trailing line break to the line.
     *
     * @param line The line to load
     * @return The parsed line
     * @throws Exception
     */
    @Override
    public String parse(String line) throws Exception {
        if (line.contains(commentToken)) {
            return line.substring(0, line.indexOf(commentToken));
        } else {
            return line;
        }
    }
}
