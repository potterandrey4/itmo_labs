package tools;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.util.regex.Pattern;


@FacesConverter(value = "tools.XCoordinateConverter", managed = true)
public class XConverter implements Converter<Double> {
	@Override
	public Double getAsObject(FacesContext facesContext, UIComponent uiComponent, String x) {
		if (x == null || x.isEmpty()) {
			return null;
		} else {
			if (!Pattern.matches("(?:-5|\\+?3)(?:[.,]0{1,15})?|(?:-[43210]|\\+?[012])(?:[.,]\\d{1,15})?", x)) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "X: Значение не соответствует шаблону.", ""));
			}
			return Double.parseDouble(x.replace(",", "."));
		}
	}


	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Double x) {
		if (x == null) {
			return "";
		} else {
			return x.toString();
		}
	}

}
