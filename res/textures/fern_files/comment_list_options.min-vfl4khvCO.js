(function(){define(["jquery","external/react","modules/core/i18n","modules/clean/activity/activity","modules/clean/react/bubble_dropdown","modules/clean/react/file_comments/logger","modules/clean/react/react_i18n","modules/clean/react/sprite_div","modules/clean/comments/action_creators","modules/clean/comments/events","modules/clean/comments/utils","modules/constants/legacy"],function(e,s,o,t,i,n,r,d,a,c,l,u){var p,m,b,h,_,f,v;return f=o._,h=r.R_,v=s.DOM,m=s.createFactory(i),_=s.createFactory(d),p=t.ActivityContext,b=s.createClass({displayName:"CommentListOptions",mixins:[s.addons.PureRenderMixin],propTypes:{activity:s.PropTypes.object,show_resolved:s.PropTypes.bool.isRequired,num_resolved:s.PropTypes.number.isRequired,is_subscribed:s.PropTypes.bool.isRequired,feedback_off:s.PropTypes.bool.isRequired,activity_context:s.PropTypes.number.isRequired,shouldShowDisableButton:s.PropTypes.bool.isRequired,shouldShowHideButton:s.PropTypes.bool.isRequired,user:s.PropTypes.object,isOffline:s.PropTypes.bool},getDefaultProps:function(){return{isOffline:!1}},_hide_comments:function(){return this.refs.dropdown.hide(),null!=this.props.activity&&n.log_event(u.FILE_ACTIVITY_LOG_EVENT_TYPE.CLIENT_COMMENTS_HIDDEN,this.props.activity.activity_key,null,null,this.props.user.id,this.props.activity_context),a.hideComments(),c.emit("hide-comments-pane")},_toggle_show_resolved:function(){return 0===this.props.num_resolved||this.props.feedback_off?void 0:(this.refs.dropdown.hide(),this.props.show_resolved?a.hideResolvedComments():a.showResolvedComments())},_isSubscribeDisabled:function(){return this.props.feedback_off||this.props.isOffline},_toggle_subscribe:function(){return this._isSubscribeDisabled()?void 0:(this.refs.dropdown.hide(),a.setCommentSubscribed({subscribed:!this.props.is_subscribed}))},_isFeedbackSettingDisabled:function(){return this.props.isOffline},_turn_off_feedback:function(){return this._isFeedbackSettingDisabled()?void 0:(this.refs.dropdown.hide(),a.setCommentsEnabled({enabled:!1}))},render:function(){var e,o,t,i,n,r,d,a,c,l,u;return i=[],this.props.shouldShowHideButton&&(t=v.a({key:"hide",className:"comments-header-menu-option hide",onMouseUp:this._hide_comments},_({group:"web",name:"s_hide",text:f("Hide comments")})),i.push(t)),d=f(this.props.show_resolved?"Hide resolved comments":"Show resolved comments"),n=s.addons.classSet({"comments-header-menu-option":!0,"toggle-resolved":!0,disabled:0===this.props.num_resolved||this.props.feedback_off}),r=v.a({key:"resolved",className:n,onMouseUp:this._toggle_show_resolved},_({group:"web",name:"s_resolve",text:d})),i.push(r),this.props.is_subscribed?(u=f("Unsubscribe from notifications"),c="s_notifications_off"):(u=f("Subscribe to notifications"),c="s_notifications_on"),a=s.addons.classSet({"comments-header-menu-option":!0,"toggle-subscribe":!0,disabled:this._isSubscribeDisabled()}),l=v.a({key:"subscribe",className:a,onMouseUp:this._toggle_subscribe},_({group:"web",name:c,text:u})),i.push(l),this.props.shouldShowDisableButton&&(e=s.addons.classSet({"comments-header-menu-option":!0,"toggle-feedback-setting":!0,disabled:this._isFeedbackSettingDisabled()}),o=v.a({key:"toggle-feedback-setting",className:e,onMouseUp:this._turn_off_feedback},_({group:"web",name:"s_disable",text:f("Disable comments")})),i.push(o)),v.span({className:"options"},m({ref:"dropdown",targetButton:v.button({className:"button-as-link"},h({},"Options")),arrow_position:"top-right",vertical_displacement:8,extra_css_class:"comments-header-bubble-dropdown"},i))}})})}).call(this);