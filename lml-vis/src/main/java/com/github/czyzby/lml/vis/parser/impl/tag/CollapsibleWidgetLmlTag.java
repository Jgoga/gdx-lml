package com.github.czyzby.lml.vis.parser.impl.tag;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.tag.AbstractActorLmlTag;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.kotcrab.vis.ui.widget.CollapsibleWidget;
import com.kotcrab.vis.ui.widget.VisTable;

/** Handles {@link CollapsibleWidget} actor. Can be used to manage one child. Converts text to a {@link Table} with a
 * label child. If the child is not a table, will create an empty table and put the child actor in it. Mapped to
 * "collapsible", "verticalCollapsible", "collapsibleWidget".
 *
 * @author MJ */
public class CollapsibleWidgetLmlTag extends AbstractActorLmlTag {
    public CollapsibleWidgetLmlTag(final LmlParser parser, final LmlTag parentTag, final StringBuilder rawTagData) {
        super(parser, parentTag, rawTagData);
    }

    @Override
    protected Actor getNewInstanceOfActor(final LmlActorBuilder builder) {
        return new CollapsibleWidget();
    }

    @Override
    protected void handlePlainTextLine(final String plainTextLine) {
        addChild(wrapWithTable(toLabel(plainTextLine)));
    }

    /** @param actor will be put a table.
     * @return a new table with 1 child. */
    protected Table wrapWithTable(final Actor actor) {
        final Table table = new VisTable();
        table.add(actor);
        return table;
    }

    @Override
    protected void handleValidChild(final LmlTag childTag) {
        if (childTag.getActor() instanceof Table) {
            addChild((Table) childTag.getActor());
        } else {
            addChild(wrapWithTable(childTag.getActor()));
        }
    }

    /** @param child will be set as collapsible's child. */
    protected void addChild(final Table child) {
        final CollapsibleWidget container = getCollapsibleWidget();
        if (GdxArrays.isNotEmpty(container.getChildren())) {
            getParser().throwErrorIfStrict("Collapsible widget can manage only one child.");
        }
        container.setTable(child);
    }

    /** @return casted actor. */
    protected CollapsibleWidget getCollapsibleWidget() {
        return (CollapsibleWidget) getActor();
    }
}
