package generators;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: daspan
 * Date: 5/27/13
 * Time: 12:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class CCOMPRule implements  Rule{
    @Override
    public boolean apply(Node currentNode, Headline output, int maxHeadlineLength) {
        int currentHeadlineSize = output.getHeadlineSize();
        if (currentNode.getWord().length() + 1 + currentHeadlineSize > maxHeadlineLength) {
            return false;
        }

        RuleEvaluator eval = new RuleEvaluator();
        Headline h = eval.evaluateRules(currentNode, maxHeadlineLength - currentHeadlineSize);

        Node governor = currentNode.getParent();
        int govIndex = output.lastIndexOf(governor);

        assert govIndex >= 0;

        int hSize = h.size();
        for (int i=0; i < hSize; i++){
            Node n = h.removeLast();
            if (!output.contains(n))
                output.add(govIndex + 1, n);
        }

        for (Node n: currentNode.getDependents())
            n.setModified(true);

        return true;
    }
}
