/*
 * Copyright (C) 2007 Lalit Pant <pant.lalit@gmail.com>
 *
 * The contents of this file are subject to the GNU General Public License 
 * Version 2 or later (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.gnu.org/copyleft/gpl.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 */

package net.xofar.util.collection;

import java.util.HashMap;

/**
 * A class that is meant to provide features missing from standard Java Maps
 * (like defaut values).
 * 
 * This class should really be built using delegantion, so it can warp any kind
 * of Map
 * 
 * @author lalitp
 */
public class SmartMap<K, V>
        extends HashMap<K, V>
{
    private static final long serialVersionUID = 6516846899477317758L;
    private V defValue;

    private SmartMap()
    {
    // disallow creation without def value
    }

    public SmartMap(V defValue)
    {
        super();
        this.defValue = defValue;
    }

    @Override
    public V get(Object key)
    {
        V ret = super.get(key);
        return ret != null
                ? ret
                : defValue;
    }
}
