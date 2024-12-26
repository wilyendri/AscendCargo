package com.api.AscendCargo.service;

import com.api.AscendCargo.exceptions.ForeignKeyException;
import com.api.AscendCargo.exceptions.InvalidFormatException;
import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.Item;
import com.api.AscendCargo.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepo itemRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo)
    {
        this.itemRepo = itemRepo;
    }

    public Item getItemById(Long id)
    {
        return itemRepo.findById(id).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public List<Item> getItems()
    {
        return itemRepo.findAll();
    }

    public Item createItem(Item item)
    {
        if(!isSkuValid(item.getSku()))
        {
            throw new InvalidFormatException("sku format is invalid");
        }

        Optional<Item> itemBySku = itemRepo.findBySku(item.getSku());
        if(itemBySku.isPresent())
        {
            throw new NotFoundException("Item already exists");
        }

        try
        {
            return itemRepo.save(item);
        }  catch (DataIntegrityViolationException e)
        {
            throw new ForeignKeyException("Foreign Key violation.");
        }
    }

    private boolean isSkuValid(String sku)
    {
        if(sku.isEmpty() || sku.isBlank())
        {
            return false;
        }

        //13-GM09
        String firstTwoDigits = sku.substring(0, 2);
        String dash = sku.substring(2, 3);
        String gm = sku.substring(3, 5);
        String lastTwoDigits = sku.substring(5, 7);

        return Integer.parseInt(firstTwoDigits) <= 99
                && Integer.parseInt(firstTwoDigits) >= 0 && dash.equals("-")
                && gm.equals("GM") && Integer.parseInt(lastTwoDigits) >= 0 && Integer.parseInt(lastTwoDigits) <= 99;
    }

}
