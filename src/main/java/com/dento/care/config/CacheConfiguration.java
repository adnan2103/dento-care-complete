package com.dento.care.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.dento.care.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.ContactNumber.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Email.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Patient.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Patient.class.getName() + ".treatments", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Patient.class.getName() + ".appointments", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Patient.class.getName() + ".contactNumbers", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Patient.class.getName() + ".emails", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Patient.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Treatment.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Treatment.class.getName() + ".notes", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Treatment.class.getName() + ".oralExaminations", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Treatment.class.getName() + ".payments", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Treatment.class.getName() + ".preTreatmentImages", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Treatment.class.getName() + ".postTreatmentImages", jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Notes.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Appointment.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.OralExamination.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.PreTreatmentImage.class.getName(), jcacheConfiguration);
            cm.createCache(com.dento.care.domain.PostTreatmentImage.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
