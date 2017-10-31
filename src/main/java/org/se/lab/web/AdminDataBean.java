package org.se.lab.web;

import org.se.lab.data.Community;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sattlerb on 31/10/2017.
 */
@Named
@RequestScoped
public class AdminDataBean {

    List<Community> requestedCommunityList;

    @PostConstruct
    public void init()
    {
        requestedCommunityList = new ArrayList<>();

        //DummyData instead of findPendingCommunities()
        Community com1 = new Community(1, "Billing", "Billing Community");
        Community com2 = new Community(2, "ProMana", "Projectmanagement Community");
        requestedCommunityList.add(com1);
        requestedCommunityList.add(com2);
    }

    private void declineRequestedCommunity()
    {
        System.out.println("Community declined");
        //throw new NotImplementedException();
    }

    private void approveRequestedCommunity()
    {
        System.out.println("Community approved");
        //throw new NotImplementedException();
    }

}
