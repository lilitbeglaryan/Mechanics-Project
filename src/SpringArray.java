import java.util.*;

public class SpringArray {


    private Spring evaluate( String type, ArrayList<Spring> springs){
        Spring answer = springs.size() > 0 ? springs.get(0) : new Spring(); // if we have no springs,
        // we create a new one(With default stiffness 1 and  inside if, return it

        if(springs.size() == 1){  // if only one spring is attached to itself,
            // i.e. it is only that spring, its stiffness will remain the same
            return answer;
        }

        for(int i = 1; i < springs.size(); i++){ // otherwise, if we have more than 1 spring
            if(type == "inParallel"){
                answer = answer.inParallel(springs.get(i));
            }else if(type == "inSeries"){
                answer = answer.inSeries(springs.get(i));
            }
        }
        return answer;
    }

    public Spring equivalentSpring(String expression){

        HashMap<Integer, ArrayList<Spring>> map = new HashMap<Integer, ArrayList<Spring>>();
        Deque<Character> stack = new ArrayDeque<Character>();
        ArrayList<Spring> springsList = new ArrayList<Spring>();

        int depth = 0; // shows the layers in the expression, at each depth several springs may be connected either parallelly or serially, so
        //at each depth the map keeps the list of all the springs at the same level.
        char lastInStack;
        char prev_symbol;
        char current_symbol;
        char next_symbol;
        Spring answer = new Spring();

        for(int index = 0; index < expression.length(); index++) { // go over the brackets and braces starting from the LHS

            if(index-1 >=0) //if the left hand-side neighbour of teh current symbol(either {,},[,])
                // is not at the 0th symbol, take that symbol, otherwise take an empty string,
                // this is the case to handle when current index is 0 and index-1 will be out of thebounds
                prev_symbol=expression.charAt(index-1);
            else
                prev_symbol=' ';

            current_symbol = expression.charAt(index);

            if(index < expression.length()-1) //if there is still at least one symbol left to consider, take it
                next_symbol=expression.charAt(index+1);
            else
                next_symbol=' '; // else take a space char instead

            if (current_symbol == '{' ){ // if the opening symbol shows in-series connection
                stack.push(current_symbol);
                if(next_symbol == '{' || next_symbol == '['){
                    ++depth;
                    map.put(depth, new ArrayList<Spring>());
                }

                continue; // go further
            }else if(current_symbol == '['){ // if the opening spring is conencted in parallel
                stack.push(current_symbol);
                if(next_symbol == '[' || next_symbol == '{'){
                    ++depth; // add one more layer , go deeper in the expression
                    map.put(depth, new ArrayList<Spring>());
                }
                continue; // go to the nect symbol  in the expression
            }

            if(current_symbol == '}'){ // if we have reached a closing in series symbol
                lastInStack = stack.pop(); // take out the recently pushed opening symbol
                if(lastInStack == '{'){ // if it matches correctly
                    if(prev_symbol == '['){
                        Spring spring = new Spring();
                        springsList.add(spring);
                        map.get(depth).add(spring); // as the obtained spring is in parallel connection, no need to consider its stiffness through evaluate()
                    }else{
                        Spring spring = evaluate("inSeries",map.get(depth));
                        depth--;
                        if(depth > 0 ){ // if we have not reached the most outer spring
                            map.get(depth).add(spring); //add to the spring set of that depth
                        }else{
                            answer = spring;
                            break;
                        }
                    }
                }

                continue;
            }else if(current_symbol == ']'){
                lastInStack = stack.pop();
                if(lastInStack == '['){
                    Spring spring = evaluate("inParallel",map.get(depth));
                    depth--; // go to the spring one layer up
                    if(depth > 0 ){ // if have not reached the outermost layer
                        map.get(depth).add(spring);
                    }else{
                        answer = spring;
                        break;
                    }
                }

            }

        }

        return answer;

    }



}
