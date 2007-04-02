/*
 * Created on Feb 5, 2005
 */
package net.xofar.jiva.samples.dejong;

import net.xofar.jiva.population.Population;


/**
 * @author LPant
 * 
 * Description
 */
public class GeneticEventListener implements /*GeneticEventListener,*/ net.xofar.util.listener.EventListener
{
  ResultEvaluator evaluator;
  
  public GeneticEventListener()
  {
    evaluator = new ResultEvaluator();
  }
  
//  public void geneticEventFired(GeneticEvent firedEvent)
//  {
//    Genotype g = (Genotype)firedEvent.getSource();
//    List individuals = g.getPopulation().getChromosomes();
//    evaluator.go(individuals);
//  }
  
  public void eventFired(Object event)
  {
      Population p = (Population)event;
      evaluator.goNew(p);
  }
  

  /**
   * 
   */
  public void stop()
  {
    evaluator.stop();
  }
}
