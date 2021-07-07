package openapi.model.v310;

import java.net.URI;

/**
 * A metadata object that allows for more fine-tuned XML model definitions.
 *
 * @param name      Replaces the name of the element/attribute used for the described schema property. <br/>
 *                  When defined within {@code items}, it will affect the name of the individual XML elements within the list. <br/>
 *                  When defined alongside type being {@code array} (outside the {@code items}), it will affect the wrapping element and only if wrapped is {@code true}. <br/>
 *                  If wrapped is {@code false}, it will be ignored.
 * @param namespace The URI of the namespace definition.
 * @param prefix    The prefix to be used for the {@code name}.
 * @param attribute Declares whether the property definition translates to an attribute instead of an element. <br/>
 *                  Default value is {@code false}.
 * @param wrapped   May be used only for an {@code array} definition. <br/>
 *                  Signifies whether the array is wrapped (for example, {@code <books><book/><book/></books>}) or unwrapped ({@code <book/><book/>}). <br/>
 *                  Default value is {@code false}. <br/>
 *                  The definition takes effect only when defined alongside type being {@code array} (outside the {@code items}).
 */
public record XML(String name, URI namespace, String prefix, Boolean attribute, Boolean wrapped) {

    /**
     * Creates an instance of a {@code XML} record class. <br/>
     * If the {@code attribute} parameter is {@code null}, the value is set to {@code false}. <br/>
     * If the {@code wrapped} parameter is {@code null}, the value is set to {@code false}.
     *
     * @param name      Replaces the name of the element/attribute used for the described schema property. <br/>
     *                  When defined within {@code items}, it will affect the name of the individual XML elements within the list. <br/>
     *                  When defined alongside type being {@code array} (outside the {@code items}), it will affect the wrapping element and only if wrapped is {@code true}. <br/>
     *                  If wrapped is {@code false}, it will be ignored.
     * @param namespace The URI of the namespace definition.
     * @param prefix    The prefix to be used for the {@code name}.
     * @param attribute Declares whether the property definition translates to an attribute instead of an element. <br/>
     *                  Default value is {@code false}.
     * @param wrapped   May be used only for an {@code array} definition. <br/>
     *                  Signifies whether the array is wrapped (for example, {@code <books><book/><book/></books>}) or unwrapped ({@code <book/><book/>}). <br/>
     *                  Default value is {@code false}. <br/>
     *                  The definition takes effect only when defined alongside type being {@code array} (outside the {@code items}).
     */
    public XML {
        if (attribute == null) attribute = false;
        if (wrapped == null) wrapped = false;
    }

}
