

package com.web2.model.dao.interfaces;

import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;
import com.web2.model.beans.Roupa;


public interface IRoupaDAO extends IDAO<Roupa, Integer>{
    
    public ArrayList<Roupa> selectAll() throws DAOException;

}
