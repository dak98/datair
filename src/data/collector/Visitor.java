package data.collector;

import data.source.Source;

/**
 * Abstract class for visitors of {@link data.collector.AirDataCollector}.
 */
public abstract class Visitor {
    /**
     * @param airDataCollector
     * @param args
     *         Parameters of the visitor.
     * @param dataSource
     *         Source for getting new data from.
     * @return
     */
    public abstract Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource);
}
