package com.heagle.monitor.service;

import com.heagle.monitor.model.Alert;

/**
 * author: hui.zhang
 */
public interface AlertListener {

    public void notify(Alert alert);
}
