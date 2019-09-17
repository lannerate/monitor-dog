package com.monitor.service;

import com.monitor.model.Alert;

/**
 * author: hui.zhang
 */
public interface AlertListener {

    public void notify(Alert alert);
}
