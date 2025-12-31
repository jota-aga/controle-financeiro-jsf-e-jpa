package converter;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import entity.Categoria;

@FacesConverter(forClass = CategoriaConverter.class)
public class CategoriaConverter implements Converter{
	
	private List<Categoria> categorias;

	public CategoriaConverter(List<Categoria> categorias) {
		super();
		this.categorias = categorias;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.isEmpty()) {
			return null;
		}
		
		for(Categoria c : categorias) {
			if(c.getId() == Integer.valueOf(value)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Categoria categoria = (Categoria) value;
			
			return String.valueOf(categoria.getId());
		}
		
		return "";
	}

}
