

package com.web2.model.facade;

import com.web2.model.dao.RoupaDAO;
import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;
import com.web2.model.beans.Roupa;
import com.web2.model.exceptions.DeleteRoupaException;
import com.web2.model.exceptions.GetAllRoupasException;
import com.web2.model.exceptions.GetRoupaException;
import com.web2.model.exceptions.InsertRoupaException;
import com.web2.model.exceptions.UpdateRoupaException;
import com.web2.model.exceptions.ViolacaoConstraintException;


public class LogicaRoupa {

    public static void insertRoupa(Roupa rp) throws ViolacaoConstraintException, InsertRoupaException{
        try {
            new RoupaDAO().insert(rp);
        } catch (ViolacaoConstraintException ex) {
            throw new ViolacaoConstraintException(ex.getMessage(), ex);
        } catch (DAOException ex) {
            throw new InsertRoupaException(ex.getMessage() + " CONTATE O DESENVOLVEDOR.", ex);
        }
    }
    
    public static void updateRoupa(Roupa rp) throws ViolacaoConstraintException, UpdateRoupaException{
        try{
            new RoupaDAO().update(rp);
        }catch (ViolacaoConstraintException ex) {
            throw new ViolacaoConstraintException(ex.getMessage(), ex);
        } catch (DAOException ex) {
            throw new UpdateRoupaException(ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
    public static void deleteRoupa(Integer id) throws DeleteRoupaException{
        try{
            new RoupaDAO().delete(id);
        }catch (DAOException ex) {
            throw new DeleteRoupaException(ex.getMessage() + " CONTATE O DESENVOLVEDOR", ex);
        }
    }
    
    public static ArrayList<Roupa> getAllRoupas() throws GetAllRoupasException {
        try {
            return new RoupaDAO().selectAll();
        } catch (DAOException ex) {
            throw new GetAllRoupasException(ex.getMessage(), ex);
        }
    }
    
    public static Roupa getRoupa(Integer id) throws GetRoupaException{
        try {
            return new RoupaDAO().selectById(id);
        } catch (DAOException ex) {
            throw new GetRoupaException(ex.getMessage(), ex);
        }
        
    }
}
